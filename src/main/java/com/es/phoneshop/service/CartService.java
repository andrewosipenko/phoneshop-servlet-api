package com.es.phoneshop.service;

import com.es.phoneshop.model.Cart;
import jakarta.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);

    void add(Long productId, int quantity, Cart cart);

    void update(Long productId, int quantity, Cart cart);

    void delete(Long productId, Cart cart);
}
