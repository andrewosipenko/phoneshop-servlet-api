package com.es.phoneshop.web;

import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.DefaultCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MiniCartServlet extends HttpServlet {

    public static final String MINI_CART_PAGE_JSP = "/WEB-INF/pages/miniCart.jsp";
    CartService cartService;

    @Override
    public void init() {
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher(MINI_CART_PAGE_JSP).include(request, response);
    }
}