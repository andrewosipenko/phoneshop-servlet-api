package com.es.phoneshop.cart;

import com.es.phoneshop.model.product.Product;

public class CartItem {
    private final Product product;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
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
