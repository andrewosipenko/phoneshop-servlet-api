package com.es.phoneshop.web;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.cart.DefaultCartService;
import com.es.phoneshop.model.product.*;
import com.es.phoneshop.recentViewd.RecentViewed;
import com.es.phoneshop.recentViewd.RecentViewedContainer;
import com.es.phoneshop.recentViewd.RecentViewedList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private RecentViewed recentViewed;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
        recentViewed = RecentViewedContainer.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getPathInfo().substring(1);
        Product product;
        if (Pattern.matches("^[0-9]+$", productId)) {
            product = productDao.getProduct(Long.valueOf(productId)).orElse(null);
        } else {
            product = null;
        }
        if (product == null) {
            response.setStatus(404);
            request.setAttribute("id", productId);
            request.getRequestDispatcher("/WEB-INF/pages/errorProductNotFound.jsp").forward(request, response);
        } else {
            request.setAttribute("product", product);
            request.setAttribute("cart", cartService.getCart(request));
            RecentViewedList recentViewedList = recentViewed.getRecentViewedList(request);
            recentViewed.addToRecentViewed(recentViewedList, product);
            request.setAttribute("recentViewedList", recentViewedList.getItems());
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getPathInfo().substring(1);
        String quantity = request.getParameter("quantity");
        Product product = productDao.getProduct(Long.valueOf(productId)).orElse(null);

        int quantityInt;
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        try {
            quantityInt = format.parse(quantity).intValue();
        } catch (ParseException e) {
            request.setAttribute("error", "Not a number");
            doGet(request, response);
            return;
        }

        if (quantityInt > product.getStock()) {
            request.setAttribute("error", "Out of stock available " + product.getStock());
            doGet(request, response);
        } else {
            if (quantityInt != 0) {
                Cart cart = cartService.getCart(request);
                cartService.add(cart, Long.valueOf(productId), quantityInt);
                response.sendRedirect(request.getContextPath() + "/products/" + productId
                        + "?message=Product added to cart");
            }
        }
    }

}

