package com.es.phoneshop.core.cart;

import com.es.phoneshop.core.dao.product.Product;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Product product;
    private Integer quantity;

    public CartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem(Long productId, Integer quantity) {
        product = new Product();
        product.setId(productId);
        this.quantity = quantity;
    }

    public CartItem(CartItem cartItem) {
        this.product = cartItem.product;
        this.quantity = cartItem.quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
