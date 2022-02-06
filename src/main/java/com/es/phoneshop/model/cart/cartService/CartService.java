package com.es.phoneshop.model.cart.cartService;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);
    void add(Cart cart, Long productId, int quantity) throws OutOfStockException;
    void addToRecentlyViewed(Cart cart, Product product, int numberOfDisplayedProducts);
}
