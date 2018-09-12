package com.es.phoneshop.exceptions;

public class NotEnoughException extends RuntimeException{
    public static final String NOT_ENOUGH_MESSAGE = "Sorry, we don't have such amount";
    public NotEnoughException(String s) {
        super(s);
    }
}
