package com.es.phoneshop.cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);
    void add(HttpServletRequest request, Cart cart, Long productId, int quantity);
}
