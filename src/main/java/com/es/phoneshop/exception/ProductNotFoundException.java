package com.es.phoneshop.exception;

import java.util.function.Supplier;

public class ProductNotFoundException extends RuntimeException implements Supplier<RuntimeException> {
    private Long id;

    public ProductNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public ProductNotFoundException get() {
        return new ProductNotFoundException(getId());
    }
}
