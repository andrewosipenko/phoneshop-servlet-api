package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;

public class CartItem implements Serializable {

    private Product product;

    private int quantity;

    private static final long serialVersionUID = 22342L;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return product.getDescription() +
                " quantity:" + quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
