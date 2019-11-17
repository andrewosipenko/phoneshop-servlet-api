package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.math.BigDecimal;

public class MiniCart implements Serializable {

    private int totalQuantity;

    private BigDecimal totalPrice;

    public MiniCart() {
        totalPrice = BigDecimal.ZERO;
    }

    public MiniCart(Cart cart) {
        totalPrice = cart.getTotalPrice();
        totalQuantity = cart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}