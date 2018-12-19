package com.es.phoneshop.CartService;

import com.es.phoneshop.model.product.Product;

import java.util.Objects;

public class CartItem {

    private Product product;
    private int quantity;

    public Product getProduct() {
        return this.product;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public CartItem() { }

    public CartItem(Product product, int quantity) {
        if(product == null||  quantity < 0)
        throw new IllegalArgumentException("Incorrect cart arguments");

        this.product = product;
        this.quantity = quantity;

    }

    public void setProduct(Product product) {
        if(product == null)
            throw new IllegalArgumentException("Invalid product");
        this.product = product;
    }

    public void setQuantity(int quantity) {
        if(quantity < 0)
            throw new IllegalArgumentException("Invalid quantity");
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || this.getClass() != object.getClass())
            return false;
        CartItem cartItem = (CartItem) object;
        return this.getQuantity() == cartItem.getQuantity() &&
                this.getProduct().equals(cartItem.getProduct());

    }

    @Override
    public int hashCode(){
        return Objects.hash(this.product, this.quantity);
    }

    @Override
    public String toString() {
        return product.toString() +
                " - " + quantity ;

    }
}
