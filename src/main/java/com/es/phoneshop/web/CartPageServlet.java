package com.es.phoneshop.web;

import com.es.phoneshop.model.Cart;
import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.impl.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends HttpServlet {


    CartService cartService;

    @Override
    public void init() throws ServletException {
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = cartService.getCart(req);
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] productId = req.getParameterValues("productId");
        String[] quantity = req.getParameterValues("quantity");

        Map<Long, String> errors = new HashMap<>();

        for (int i = 0; i < productId.length; i++) {
            Long productIdL = Long.parseLong(productId[i]);
            try {
                Long quantityL = Long.parseLong(quantity[i]);
                cartService.update(cartService.getCart(req), productIdL, quantityL);
            } catch (Exception e) {
                errors.put(productIdL, e.getMessage());
            }
        }
        req.setAttribute("errors", errors);
        doGet(req, resp);
    }
}
