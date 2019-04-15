package com.es.phoneshop.core.exceptions;

import javax.servlet.ServletException;

public class OutOfStockException extends ServletException {
    public OutOfStockException(String message) {
        super(message);
    }
}
