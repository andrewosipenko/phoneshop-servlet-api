package com.es.phoneshop.model.exception;

public class IllegalQuantityException extends RuntimeException {
    public IllegalQuantityException() {
        super();
    }

    public IllegalQuantityException(String message) {
        super(message);
    }

    public IllegalQuantityException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalQuantityException(Throwable cause) {
        super(cause);
    }
}
