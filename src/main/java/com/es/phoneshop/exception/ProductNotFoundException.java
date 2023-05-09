package com.es.phoneshop.exception;

import java.util.NoSuchElementException;

public class ProductNotFoundException extends NoSuchElementException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
