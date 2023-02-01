package com.es.phoneshop.exception;

import com.es.phoneshop.model.product.Product;

public class OutOfStockException extends Exception {
    private Product product;
    private int requestedQuantity;
    private int stock;

    public OutOfStockException(Product product, int requestedQuantity, int stock) {
        this.product = product;
        this.requestedQuantity = requestedQuantity;
        this.stock = stock;
    }

    public Product getProduct() {
        return product;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public int getStock() {
        return stock;
    }
}
