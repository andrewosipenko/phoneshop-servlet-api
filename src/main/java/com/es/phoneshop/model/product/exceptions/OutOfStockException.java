package com.es.phoneshop.model.product.exceptions;

import javax.servlet.ServletException;

public class OutOfStockException extends ServletException {
    public OutOfStockException(String message) {
        super(message);
    }
}
