package com.es.phoneshop.model.exception;

public class LackOfStockException extends RuntimeException {
    public LackOfStockException() {
        super();
    }

    public LackOfStockException(String message) {
        super(message);
    }

    public LackOfStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public LackOfStockException(Throwable cause) {
        super(cause);
    }
}
