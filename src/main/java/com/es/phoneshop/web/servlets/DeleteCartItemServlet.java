package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.entity.Cart;
import com.es.phoneshop.model.cart.service.CartService;
import com.es.phoneshop.model.cart.service.HttpServletCartService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartItemServlet extends HttpServlet {
    private CartService<HttpServletRequest> cartService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        cartService = HttpServletCartService.INSTANCE;

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productId = request.getPathInfo().substring(1);
        Cart cart = cartService.getCart(request);
        cartService.delete(cart, Long.valueOf(productId));
        response.sendRedirect(request.getContextPath() + "/cart?message=Cart item removed successfully");
    }
}
