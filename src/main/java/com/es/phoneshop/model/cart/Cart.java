package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private List<CartItem> cartItems;

    private int totalQuantity;

    private BigDecimal totalCost;

    private static final long serialVersionUID = 3L;

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Cart() {
        this.cartItems = new ArrayList<>();
        this.totalCost = BigDecimal.ZERO;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    @Override
    public String toString() {
        return cartItems.toString();
    }
}
