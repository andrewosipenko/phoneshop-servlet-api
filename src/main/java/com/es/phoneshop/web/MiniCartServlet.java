package com.es.phoneshop.web;

import com.es.phoneshop.model.Cart;
import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.impl.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MiniCartServlet extends HttpServlet {
    CartService cartService;

    @Override
    public void init() throws ServletException {
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = cartService.getCart(req);
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/WEB-INF/pages/miniCart.jsp").include(req, resp);
    }
}
