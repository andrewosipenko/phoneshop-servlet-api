package com.es.phoneshop.services;

import com.es.phoneshop.model.Cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest httpServletRequest);

    void add(Cart cart, Long productId, Long quantity);

    void update(Cart cart, Long productId, Long quantity);

    void delete(Cart cart, Long ProductId);

    void clearCart(Cart cart);
}
