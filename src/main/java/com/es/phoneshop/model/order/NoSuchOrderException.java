package com.es.phoneshop.model.order;

public class NoSuchOrderException extends RuntimeException {
    public NoSuchOrderException(Long id) {
        super("No order with " + id +" id.");
    }
}
