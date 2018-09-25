package com.es.phoneshop.model.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AbstractOrder <T extends AbstractOrderItem> implements Serializable{
    private List<T> cartItems = new ArrayList<>();

    public List<T> getCartItems(){
        return cartItems;
    }

    public void setCartItems(List<T> cartItems) {
        this.cartItems = cartItems;
    }

}
