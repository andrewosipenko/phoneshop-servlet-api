package com.es.phoneshop.model.product.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<CartItem> itemToRemove = cartItems.stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(idToRemove))
                .findFirst();
        if (itemToRemove.isPresent()) {
            cartItems.remove(itemToRemove.get());
            recalculate();
        }
    }
}
