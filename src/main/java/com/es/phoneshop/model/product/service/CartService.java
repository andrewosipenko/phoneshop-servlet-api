package com.es.phoneshop.model.product.service;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.exception.OutOfStockException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);

    void add(Cart cart, Long productId, int quantity, HttpServletRequest request) throws OutOfStockException;
}
