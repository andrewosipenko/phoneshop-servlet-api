package com.es.phoneshop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    List<CartItem> cartItems;
    BigDecimal price;

    public Cart() {
        this.cartItems = new ArrayList<>();
        this.price = BigDecimal.ZERO;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
