package com.es.phoneshop.model.cart.entity;

import com.es.phoneshop.model.product.entity.Product;

public class CartItem {
    private Product product;

    private int quantity;

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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(int quantity){
        this.quantity+=quantity;
    }
}