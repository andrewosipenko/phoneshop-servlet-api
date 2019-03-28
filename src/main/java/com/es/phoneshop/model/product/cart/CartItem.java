package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.dao.Product;

public class CartItem {
    private Product product;
    private Integer quantity;

    CartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
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
