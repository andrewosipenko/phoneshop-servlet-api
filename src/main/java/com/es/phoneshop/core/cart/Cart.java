package com.es.phoneshop.core.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cart implements Serializable {
    private final List<CartItem> cartItems;
    private BigDecimal totalPrice;

    public Cart() {
        totalPrice = BigDecimal.ZERO;
        cartItems = new ArrayList<>();
    }

    public Cart(Cart cart) {
        cartItems = cart.getCartItems().stream()
                .map(CartItem::new)
                .collect(Collectors.toList());
        totalPrice = cart.totalPrice;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
