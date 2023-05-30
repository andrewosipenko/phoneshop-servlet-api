package com.es.phoneshop.exception;

import java.util.NoSuchElementException;

public class OrderNotFoundException extends NoSuchElementException {
    private Long orderId;

    public OrderNotFoundException() {
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
