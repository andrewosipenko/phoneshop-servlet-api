package com.es.phoneshop.service;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;

public interface CartService {
    Cart getCart(HttpSession session);

    void addToCart(Cart cart, Product product, Integer quantity);
    void updateCart(Cart cart, Product product, int quantity);
    void delete(Cart cart, Product product);
    void clearCart(Cart cart);
}
