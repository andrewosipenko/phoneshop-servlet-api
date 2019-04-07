package com.es.phoneshop.model.product.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private final List<CartItem> cartItems;
    private BigDecimal totalPrice;

    public Cart() {
        totalPrice = BigDecimal.ZERO;
        cartItems = new ArrayList<>();
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

    public void recalculate() {
        totalPrice = BigDecimal.ZERO;
        cartItems.forEach(cartItem -> setTotalPrice(totalPrice.add(
                cartItem.getProduct()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))));
    }

    public void remove(Long idToRemove) {
        cartItems.remove(cartItems.stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(idToRemove))
                .findFirst()
                .get());
        recalculate();
    }
}
