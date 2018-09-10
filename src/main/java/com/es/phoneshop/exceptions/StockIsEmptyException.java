package com.es.phoneshop.exceptions;

public class StockIsEmptyException extends RuntimeException {
    public StockIsEmptyException() {
    }

    public StockIsEmptyException(String message) {
        super(message);
    }
}
