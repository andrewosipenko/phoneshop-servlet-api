package com.es.phoneshop.model;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
    Cart getCart(HttpServletRequest request);
    void add(Cart cart, Product product, int quantity);
    void update(Cart cart, Product product, int quantity);
    void delete(Cart cart, Product product, int index);
}
