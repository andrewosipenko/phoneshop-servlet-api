package com.es.phoneshop.cart;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class CartItem implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private final Product product;
    private AtomicInteger quantity;

    public AtomicInteger getQuantity() {
        return quantity;
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
