package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exceptions.NegativeQuantityException;
import com.es.phoneshop.model.exceptions.OutOfStockException;
import com.es.phoneshop.model.exceptions.ZeroQuantityException;

import javax.servlet.http.HttpSession;

public interface CartService {
    Cart getCart(HttpSession session);

    void add(Cart cart, Long productId, int quantity) throws OutOfStockException, NegativeQuantityException, ZeroQuantityException;

    void update(Cart cart, Long productId, int quantity) throws OutOfStockException, NegativeQuantityException, ZeroQuantityException;

    void delete(Cart cart, Long productId);

    void clearCart(Cart cart);
}
