package com.es.phoneshop.listener;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.impl.CartServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

public class CartListener implements HttpSessionListener {

    private static final String CART_SESSION_ATTRIBUTE = CartServiceImpl.class.getName() + ".cart";

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        Cart cart = new Cart();
        session.setAttribute(CART_SESSION_ATTRIBUTE, cart);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSessionListener.super.sessionDestroyed(se);
    }
}

