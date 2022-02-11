package com.es.phoneshop.model.cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface CartService {
    Cart getCart(HttpServletRequest request);
    void update(Cart cart, Long productId, int quantity, HttpSession session) throws OutOfStockException;
    void add(Cart cart, Long productId, int quantity, HttpSession session) throws OutOfStockException;
}
