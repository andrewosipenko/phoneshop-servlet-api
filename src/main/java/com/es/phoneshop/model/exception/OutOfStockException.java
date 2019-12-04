package com.es.phoneshop.model.exception;

public class OutOfStockException extends RuntimeException {
    private int availableStock;

    public OutOfStockException(int availableStock) {
        this.availableStock = availableStock;
    }

    public int getAvailableStock() {
        return availableStock;
    }
}
