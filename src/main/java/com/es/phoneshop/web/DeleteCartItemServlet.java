package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.HttpSessionCartService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = request.getPathInfo().substring(1);
        Long productId = Long.parseLong(url);
        Cart cart = HttpSessionCartService.getInstance().getCart(request);
        HttpSessionCartService.getInstance().delete(cart, productId, request.getSession());
        response.sendRedirect(request.getContextPath() + "/cart?message=Product deleted from cart!");
    }
}
