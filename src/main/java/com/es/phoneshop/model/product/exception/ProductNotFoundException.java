package com.es.phoneshop.model.product.exception;

public class ProductNotFoundException extends RuntimeException{
    private String message;

    public ProductNotFoundException(String message)
    {
        this.message=message;
    }

    public ProductNotFoundException() {
    }
}
