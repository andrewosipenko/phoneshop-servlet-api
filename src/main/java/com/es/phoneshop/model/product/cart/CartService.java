package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    void add(HttpServletRequest request, Cart customerCart, CartItem cartItem) throws ProductNotFoundException, OutOfStockException;

    void save(HttpServletRequest request);

    void recalculate(Cart customerCart);

    boolean remove(Cart customerCart, Long idToRemove);

    Cart getCart(HttpServletRequest request);
}
