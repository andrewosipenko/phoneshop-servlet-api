package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.productdao.Product;

import java.io.Serializable;

public class CartItem implements Serializable, Cloneable {
    private Product cartProduct;
    private int quantity;

    public CartItem(Product cartProduct, int quantity) {
        this.cartProduct = cartProduct;
        this.quantity = quantity;
    }

    public Product getCartProduct() {
        return cartProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int number) {
        quantity += number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItem cartItem = (CartItem) o;

        if (quantity != cartItem.quantity) return false;
        return cartProduct.equals(cartItem.cartProduct);
    }

    @Override
    public int hashCode() {
        int result = cartProduct.hashCode();
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                cartProduct.getDescription() +
                ", " +
                quantity +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}