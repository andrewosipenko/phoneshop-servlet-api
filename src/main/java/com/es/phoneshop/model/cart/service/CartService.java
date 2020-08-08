package com.es.phoneshop.model.cart.service;

import com.es.phoneshop.model.cart.entity.Cart;
import com.es.phoneshop.web.exceptions.OutOfStockException;

public interface CartService<SessionResource> {

    Cart getCart(SessionResource sessionResource);

    void add(Cart cart, Long productId, int quantity) throws OutOfStockException;

    void update(Cart cart, Long productId, int quantity) throws OutOfStockException;

}
