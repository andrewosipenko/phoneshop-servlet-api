package com.es.phoneshop.model.product.exception;

public class EntityNotFoundException extends RuntimeException {
    protected String message;

    public EntityNotFoundException(String message) {
        this.message = message;
    }

    public EntityNotFoundException() {
    }

    @Override
    public String getMessage() {
        return message;
    }
}
