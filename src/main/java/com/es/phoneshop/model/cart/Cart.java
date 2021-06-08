package com.es.phoneshop.model.cart;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Cart[" + items + "]";
    }
}
