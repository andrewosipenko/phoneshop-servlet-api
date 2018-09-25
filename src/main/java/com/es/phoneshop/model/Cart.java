package com.es.phoneshop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable{
    private List<CartItem> cartItems = new ArrayList<>();


    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems){
        this.cartItems = cartItems;
    }


}
