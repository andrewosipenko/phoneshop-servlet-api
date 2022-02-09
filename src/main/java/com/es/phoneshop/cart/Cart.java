package com.es.phoneshop.cart;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class Cart {
    private final List<CartItem> items;

    public Cart() {
        this.items = new CopyOnWriteArrayList<>();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public Optional<CartItem> getCartItemByProductId(Long id) {
        for (CartItem item : items) {
            if (Objects.equals(item.getProduct().getId(), id)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "[" + items + "]";
    }
}
