package com.es.phoneshop.model.cart;

public class OutOfStockException extends Exception {
    private int stock;

    public OutOfStockException(int stock) {
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }
}
