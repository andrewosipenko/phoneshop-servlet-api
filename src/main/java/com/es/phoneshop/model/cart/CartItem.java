package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import java.io.Serializable;
import java.util.Objects;

public class CartItem implements Serializable{
    private Product product;
    private int quantity;

    public CartItem() {
    }

    public CartItem(CartItem cartItem) {
        this.product = cartItem.product;
        this.quantity = cartItem.quantity;
    }

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CartItem cartItem = (CartItem) object;
        return quantity == cartItem.quantity &&
                Objects.equals(product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }

}