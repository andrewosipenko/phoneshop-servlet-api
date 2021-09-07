package com.es.phoneshop.exceptions;

public class ProductNotFoundException extends Exception {

    private static final String message="Product with such id wasn't found. id = ";

    public ProductNotFoundException() {
        super(message);
    }

    public ProductNotFoundException(Long id) {
        super(message+id);
    }

}
