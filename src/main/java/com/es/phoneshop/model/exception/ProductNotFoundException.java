package com.es.phoneshop.model.exception;

public class ProductNotFoundException extends RuntimeException {
    private Long id;

    public ProductNotFoundException(Long id) {
        this.id = id;
    }
    public ProductNotFoundException(String message,Long i){
        super(message);
        id=i;
    }

    public Long getId() {
        return id;
    }

}
