package com.es.phoneshop.exceptions;

public class UnderZeroException extends RuntimeException {
    public static final String UNDER_ZERO_MESSAGE = "Number under zero";
    public UnderZeroException(String s) {
        super(s);
    }
}
