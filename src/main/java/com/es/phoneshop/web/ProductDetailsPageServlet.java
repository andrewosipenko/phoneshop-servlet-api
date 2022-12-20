package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.model.exceptions.NegativeQuantityException;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.exceptions.ZeroQuantityException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.RecentlyViewedProducts;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class ProductDetailsPageServlet extends HttpServlet {
    private static final String ERROR="error";
    private static final String JSP_PATH="/WEB-INF/pages/product.jsp";

    private ProductDao productDao;
    private RecentlyViewedProducts recentlyViewedProducts;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = DefaultCartService.getInstance();
        recentlyViewedProducts = RecentlyViewedProducts.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = parseProductId(request);
        recentlyViewedProducts.addProduct(request, productDao.getProduct(id));
        request.setAttribute("product", productDao.getProduct(id));
        request.setAttribute("cart", cartService.getCart(request.getSession()));
        request.setAttribute("viewedProducts", recentlyViewedProducts.getProducts(request));

        request.getRequestDispatcher(JSP_PATH).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = parseProductId(request);
        String quantityString = request.getParameter("quantity");
        Cart cart = cartService.getCart(request.getSession());
        Product product = productDao.getProduct(productId);
        int quantity;

        try {
            NumberFormat format = NumberFormat.getInstance(request.getLocale());
            quantity = format.parse(quantityString).intValue();
        } catch (ParseException e) {
            request.setAttribute(ERROR, "Not a number");
            doGet(request, response);
            return;
        }
        try {
            if (cart.containsCartItem(product)) {
                cartService.update(cart, productId, quantity);
            } else {
                cartService.add(cart, productId, quantity);
            }
        } catch (OutOfStockException | NegativeQuantityException | ZeroQuantityException e) {
            request.setAttribute(ERROR, "Out of stock, available " +product.getStock());
            doGet(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/products/" + productId + "?message=Product added to cart");
    }

    private Long parseProductId(HttpServletRequest request) {
        String productInfo = request.getPathInfo().substring(1);
        return Long.valueOf(productInfo);
    }
}
