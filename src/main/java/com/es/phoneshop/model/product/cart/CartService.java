package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    void add(Cart customerCart, Long productId, Integer quantity) throws ProductNotFoundException, OutOfStockException;
    Cart getCart(HttpServletRequest request);
}
