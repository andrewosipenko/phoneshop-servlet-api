package com.es.phoneshop.model.product;

public class ProductNotFoundException extends Exception{
    public void message(){
        System.err.println("It looks like product you're trying to find doesn't exist. Woops!");
    }
}
