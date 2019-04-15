package com.es.phoneshop.core.cart;

import com.es.phoneshop.core.exceptions.OutOfStockException;
import com.es.phoneshop.core.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    void add(HttpServletRequest request, Cart customerCart, CartItem cartItem) throws ProductNotFoundException, OutOfStockException;

    void save(HttpServletRequest request);

    void recalculate(Cart customerCart);

    boolean remove(Cart customerCart, Long idToRemove);

    Cart getCart(HttpServletRequest request);

    void clearCart(HttpServletRequest request);
}
