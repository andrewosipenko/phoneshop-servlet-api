package com.es.phoneshop.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static List<CartItem> cartItems= new ArrayList();

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems){
        this.cartItems = cartItems;
    }

}
