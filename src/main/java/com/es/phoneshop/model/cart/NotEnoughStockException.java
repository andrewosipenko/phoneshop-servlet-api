package com.es.phoneshop.model.cart;


public class NotEnoughStockException extends Exception {
    NotEnoughStockException(String message) {
        super(message);
    }
}