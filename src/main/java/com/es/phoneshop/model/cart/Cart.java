package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable{
    private List<CartItem> cartItems = new ArrayList<>();
    private BigDecimal totalPrice = new BigDecimal(0);
    private int totalQuantity;

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }
}
