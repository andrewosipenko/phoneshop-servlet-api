package com.es.phoneshop.web;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.HttpSessionCartService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init(ServletConfig config) {
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long idToRemove = getProductId(req);
        Cart cart = cartService.getCart(req);
        if (cartService.remove(cart, idToRemove)) {
            cartService.save(req);
        }
        if (cart.getCartItems().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/products");
        } else {
            resp.sendRedirect(req.getRequestURI());
        }
    }

    private Long getProductId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        return Long.valueOf(pathInfo.substring(1));
    }
}
