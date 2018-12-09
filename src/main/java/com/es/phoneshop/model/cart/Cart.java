package com.es.phoneshop.model.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    List<CartItem> cartItems = new ArrayList<>();

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public String toString() {
        return cartItems.toString();
    }
}
