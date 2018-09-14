package com.es.phoneshop.web;

import com.es.phoneshop.model.*;
import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    CartService cartService;
    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getInstance();
        cartService = CartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productID = Long.parseLong(request.getPathInfo().substring(1));
        request.setAttribute("product", productDao.getProduct(productID));
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = Long.valueOf(getLastPathPatameter(request));
        Product product = productDao.getProduct(productId);
        Integer quantity;

        try {
            Locale locale = request.getLocale();
            quantity = DecimalFormat.getInstance(locale).parse(request.getParameter("quantity")).intValue();
        } catch (ParseException ex) {
            request.setAttribute("error", "Not a number");
            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
            return;
        }

        Cart cart = cartService.getCart(request);
        cartService.add(cart, product, quantity);
        request.setAttribute("product", product);
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

    private String getLastPathPatameter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int index = uri.lastIndexOf("/");
        return uri.substring(index+1);
    }
}
