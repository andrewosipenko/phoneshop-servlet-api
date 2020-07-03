package com.es.phoneshop.web;

import com.es.phoneshop.model.Cart;
import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.impl.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartDeleteServlet extends HttpServlet {
    CartService cartService;

    @Override
    public void init() {
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long productId = getProductIdFromUrl(req);
        Cart cart = cartService.getCart(req);
        cartService.delete(cart, productId);
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private Long getProductIdFromUrl(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        return Long.parseLong(pathInfo.split("/")[1]);
    }
}
