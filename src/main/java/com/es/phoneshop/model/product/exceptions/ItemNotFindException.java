package com.es.phoneshop.model.product.exceptions;

public class ItemNotFindException extends RuntimeException {
    public ItemNotFindException(String message) {
        super(message);
    }
}