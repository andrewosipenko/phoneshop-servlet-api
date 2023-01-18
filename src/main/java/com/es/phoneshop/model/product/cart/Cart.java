package com.es.phoneshop.model.product.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    private List<CartItem> items;

    public List<CartItem> getItems() {
        return items;
    }

    public Cart() {
        items = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Cart{" + items +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(items, cart.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
