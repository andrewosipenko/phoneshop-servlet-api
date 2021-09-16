package com.es.phoneshop.model.product.exceptions;

public class QuantityLowerZeroException extends Exception{
    public QuantityLowerZeroException(String message) {
        super(message);
    }
}
