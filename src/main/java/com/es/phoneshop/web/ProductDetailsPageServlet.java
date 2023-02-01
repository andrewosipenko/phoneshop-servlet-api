package com.es.phoneshop.web;

import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.entity.Cart;
import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.model.product.exception.OutOfStockException;
import com.es.phoneshop.model.product.service.CartService;
import com.es.phoneshop.model.product.service.ProductService;
import com.es.phoneshop.model.product.service.impl.CartServiceImpl;
import com.es.phoneshop.model.product.service.impl.ProductServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

import static com.es.phoneshop.model.product.service.ProductService.POSSIBLE_ERROR_MESSAGE;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = CartServiceImpl.getInstance();
        productService = ProductServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo().substring(1).contains("/")) {
            request.setAttribute("product", productDao.getEntity(
                    productService.parseProductIdFromRequestWithHistory(request), POSSIBLE_ERROR_MESSAGE));
            request.getRequestDispatcher("/WEB-INF/pages/productPriceHistory.jsp").forward(request, response);
        } else {
            Product product = productDao.getEntity(productService.parseProductIdFromRequestWithoutHistory(request), POSSIBLE_ERROR_MESSAGE);
            request.setAttribute("product", product);
            productService.includeProductInRecentProducts(product, request);
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long productId = productService.parseProductIdFromRequestWithoutHistory(request);
        int quantity;
        Cart cart = cartService.getCart(request);
        try {
            if (!request.getParameter("quantity").matches("^\\d+([\\.\\,]\\d+)?$")) {
                throw new ParseException("Not a number!", 0);
            }
            NumberFormat numberFormat = NumberFormat.getNumberInstance(request.getLocale());
            quantity = numberFormat.parse(request.getParameter("quantity")).intValue();
        } catch (ParseException exception) {
            request.setAttribute("error", "Not a number!");
            response.sendRedirect(String.format("%s/products/%d?message=&error=Not a number!", request.getContextPath(), productId));
            return;
        }
        try {
            cartService.add(cart, productId, quantity, request);
        } catch (OutOfStockException e) {
            request.setAttribute("error", "Not enough items in stock! Available: " + e.getAvailableStock());
            response.sendRedirect(String.format("%s/products/%d?message=&error=Not enough items in stock! Available:%d", request.getContextPath(), productId, e.getAvailableStock()));
            return;
        }

        request.setAttribute("product", productDao.getEntity(productId, POSSIBLE_ERROR_MESSAGE));
        response.sendRedirect(String.format("%s/products/%d?message=Product was added to cart successfully!", request.getContextPath(), productId));
    }
}
