package com.es.phoneshop.cart;

import java.util.List;
import java.util.Vector;

public class Cart {
    private List<CartItem> cartItems;

    public Cart() {
        cartItems = new Vector<>();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public synchronized void  setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
