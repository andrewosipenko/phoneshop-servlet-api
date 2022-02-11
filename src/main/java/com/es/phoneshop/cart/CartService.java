package com.es.phoneshop.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);
    void add(HttpServletRequest request, Cart cart, Long productId, int quantity);
    boolean isEnoughStockForOrder(HttpServletRequest request, Product product, int quantity);
}
