package com.es.phoneshop.web;

import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.CartServiceImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteCartItemServlet extends HttpServlet {
    private CartService cartService;
    private static final String SUCCESSFULLY_DELETE_MESSAGE = "Cart item removed successfully";

    @Override
    public void init() {
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productId = request.getPathInfo().substring(1);
        cartService.delete(Long.valueOf(productId), cartService.getCart(request));
        response.sendRedirect(String.format("%s/cart?message=%s",
                request.getContextPath(), SUCCESSFULLY_DELETE_MESSAGE));
    }
}
