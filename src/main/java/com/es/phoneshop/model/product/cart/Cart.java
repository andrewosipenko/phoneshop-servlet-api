package com.es.phoneshop.model.product.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> cartItems;

    public Cart() {
        cartItems = new ArrayList<>();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        return cartItems.equals(cart.cartItems);
    }

    @Override
    public int hashCode() {
        return cartItems.hashCode();
    }

    @Override
    public String toString() {
        return "Cart{" +
                cartItems +
                '}';
    }
}
