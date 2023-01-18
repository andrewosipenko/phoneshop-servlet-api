package com.es.phoneshop.model.product.exception;

import com.es.phoneshop.model.product.entity.Product;

public class OutOfStockException extends Exception {
    private Product product;
    private int requestedStock;
    private int availableStock;

    public OutOfStockException(Product product, int requestedStock, int availableStock) {
        this.product = product;
        this.requestedStock = requestedStock;
        this.availableStock = availableStock;
    }

    public Product getProduct() {
        return product;
    }

    public int getRequestedStock() {
        return requestedStock;
    }

    public int getAvailableStock() {
        return availableStock;
    }
}
