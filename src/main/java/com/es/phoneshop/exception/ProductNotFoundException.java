package com.es.phoneshop.exception;

public class ProductNotFoundException extends RuntimeException {
    private final Long productId;

    public ProductNotFoundException(Long id) {
        super();
        this.productId = id;
    }

    public ProductNotFoundException(String message, Long id) {
        super(message);
        this.productId = id;
    }

    public Long getProductId() {
        return productId;
    }
}
