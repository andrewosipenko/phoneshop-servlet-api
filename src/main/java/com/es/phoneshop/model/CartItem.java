package com.es.phoneshop.model;

import java.io.Serializable;

public class CartItem implements Serializable, Cloneable {
    private Long id;
    private Product product;
    private int quantity;
    private static final long serialVersionUID = 1113L;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return product.getDescription() + " - " + quantity;
    }

    @Override
    public Object clone() {
        CartItem cartItem = null;
        try {
            cartItem = (CartItem) super.clone();
            Product product = this.getProduct().clone();
            cartItem.setProduct(product);
            return cartItem;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
