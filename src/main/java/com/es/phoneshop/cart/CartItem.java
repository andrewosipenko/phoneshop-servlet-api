package com.es.phoneshop.cart;

import com.es.phoneshop.model.product.Product;

import java.util.concurrent.atomic.AtomicInteger;

public class CartItem {
    private final Product product;
    private AtomicInteger quantity;

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity = new AtomicInteger(quantity);
    }

    public Product getProduct(){
        return product;
    }

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = new AtomicInteger(quantity);
    }

    @Override
    public String toString() {
        return product.getDescription() + " " + quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return product.getDescription().equals(cartItem.product.getDescription());
    }
}
