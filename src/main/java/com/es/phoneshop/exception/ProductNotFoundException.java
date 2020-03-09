package com.es.phoneshop.exception;

public class ProductNotFoundException extends RuntimeException {
    private Long id;
    public ProductNotFoundException(Long id) {
        this.id = id;
    }
}
