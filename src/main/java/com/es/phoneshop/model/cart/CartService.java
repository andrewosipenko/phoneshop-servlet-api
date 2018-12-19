package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;

public interface CartService {
    Cart getCart(HttpSession session);

    void addToCart(Cart cart, Product product, int quantity) throws NotEnoughStockException;

    void updateCart(Cart cart, Product product, int quantity) throws NotEnoughStockException;

    void delete(Cart cart, Product product);

    void clearCart(Cart cart);


}
