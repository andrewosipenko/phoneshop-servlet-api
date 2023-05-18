package com.es.phoneshop.exception;

public class OutOfStockException extends RuntimeException {
    private String message;

    public OutOfStockException() {
    }

    public OutOfStockException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
