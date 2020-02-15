package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.cart.MiniCart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MiniCartServlet extends HttpServlet {
    public static final String MINI_CART = "miniCart";
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(MINI_CART, new MiniCart(cartService.getCart(request.getSession())));
        request.getRequestDispatcher("/WEB-INF/fragments/miniCart.jsp").include(request, response);
    }
}
