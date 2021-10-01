package com.es.phoneshop.model.product.exceptions;

public class DateBeforeException extends Exception{
    public DateBeforeException(String message) {
        super(message);
    }
}