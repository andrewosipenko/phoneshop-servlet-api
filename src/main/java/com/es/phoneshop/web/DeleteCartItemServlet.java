package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.impl.DefaultCartService;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartItemServlet extends HttpServlet {
    private DefaultCartService cartService;

    @Override
    public void init(ServletConfig config) {
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productIdString = request.getParameter("productId");

        Cart cart = cartService.getCart(request);
        cartService.delete(cart, Long.parseLong(productIdString));

        response.sendRedirect(request.getContextPath() + "/cart?successDel=true");

    }
}
