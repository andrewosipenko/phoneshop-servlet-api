package com.es.phoneshop.model.product;

public class ProductNotFindException extends RuntimeException {
    public ProductNotFindException(String message) {
        super(message);
    }
}
