package com.es.phoneshop.cart;

import com.es.phoneshop.exceptions.IncorrectInputException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);
    void add(HttpServletRequest request, String productId, String quantity) throws IncorrectInputException;
    void update(HttpServletRequest request, String productId, String quantity) throws IncorrectInputException;
    void deleteCartItem(HttpServletRequest request, String productId);
    void clearCart(HttpServletRequest request);
}
