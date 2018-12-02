package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;

public interface CartService {
    //Cart getCart(HttpSession session);
    Cart getCart();

    void addToCart(Product product, Integer quantity);
}
