package com.es.phoneshop.web.pages;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.OutOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.HttpSessionRecentlyViewedProduct;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;

    private CartService cartService;

    private HttpSessionRecentlyViewedProduct httpSessionRecentlyViewedProduct;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        httpSessionRecentlyViewedProduct = HttpSessionRecentlyViewedProduct.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = Long.valueOf(request.getPathInfo().substring(1));
        request.setAttribute("product", productDao.getProduct(productId));
        request.setAttribute("cart", cartService.getCart(request).toString());
        request.setAttribute("viewedProducts", httpSessionRecentlyViewedProduct.getRecentlyViewedProducts(request));
        httpSessionRecentlyViewedProduct.addViewedProduct(request, productId);
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int quantity;
        String message;
        String productId = request.getPathInfo().substring(1);
        try {
            quantity = NumberFormat
                    .getInstance(request.getLocale())
                    .parse(request.getParameter("quantity"))
                    .intValue();
            verifyQuantity(quantity);
        } catch (ParseException e) {
            request.setAttribute("error", "Not a number");
            doGet(request, response);
            return;
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Negative number or zero");
            doGet(request, response);
            return;
        }
        try {
            cartService.add(request, Long.valueOf(productId), quantity);
            message = "Product added to the cart";
        } catch (OutOfStockException e) {
            request.setAttribute("error", "Out of stock, only " + e.getStock() + " left");
            doGet(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/products/" + productId + "?message=" + message);
    }

    private void verifyQuantity(int quantity) throws IllegalArgumentException {
        if (quantity <= 0) {
            throw new IllegalArgumentException();
        }
    }
}
