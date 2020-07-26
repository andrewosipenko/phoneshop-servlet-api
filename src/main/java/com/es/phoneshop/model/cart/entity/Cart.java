package com.es.phoneshop.model.cart.entity;

import com.es.phoneshop.model.product.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    //LinkedHashMap would be better?
    //Can Entity-Bean contain some business-logic?
    private List<CartItem> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return this.cartItems;
    }

    public void add(Product product, int quantity) {
        cartItems.add(new CartItem(product, quantity));
    }

    public void add(CartItem cartItem){
        cartItems.add(cartItem);
    }

    @Override
    public String toString() {
        return "Cart[" + cartItems + ']';
    }
}
