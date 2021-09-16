package com.es.phoneshop.model.product.exceptions;

public class ProductNotFindException extends RuntimeException {
    public ProductNotFindException(String message) {
        super(message);
    }
}
