package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.Product;

public class CartItem {
    private Product cartProduct;
    private int quantity;

    public CartItem(Product certProduct, int quantity) {
        this.cartProduct = certProduct;
        this.quantity = quantity;
    }

    public Product getCartProduct() {
        return cartProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int number) {
        quantity += number;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                cartProduct.getDescription() +
                ", " +
                quantity +
                '}';
    }
}
