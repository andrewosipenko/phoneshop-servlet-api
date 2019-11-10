package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

public class CartItem {
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

}
