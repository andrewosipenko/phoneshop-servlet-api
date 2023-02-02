package com.es.phoneshop.service;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);
    void add(Long productId, int quantity, HttpServletRequest request) throws OutOfStockException;
    void update(Long productId, int quantity, HttpServletRequest request) throws OutOfStockException;
    void delete(Long productId, HttpServletRequest request);
    void clearCart(HttpServletRequest request);
}
