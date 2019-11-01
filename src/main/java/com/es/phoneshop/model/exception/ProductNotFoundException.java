package com.es.phoneshop.model.exception;

public class ProductNotFoundException extends RuntimeException {
    private Long id;
    public Long getId(){return id; }
    public ProductNotFoundException(Long i){
        id=i;
    }
    public ProductNotFoundException(String message,Long i){
        super(message);
        id=i;
    }
}
