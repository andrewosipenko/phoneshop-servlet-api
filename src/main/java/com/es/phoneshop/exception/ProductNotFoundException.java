package com.es.phoneshop.exception;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class ProductNotFoundException extends NoSuchElementException implements Supplier<RuntimeException> {
    private Long id;

    public ProductNotFoundException(Long id) {
        this.id = id;
    }

    public ProductNotFoundException() {

    }

    public Long getId() {
        return id;
    }

    @Override
    public ProductNotFoundException get() {
        return new ProductNotFoundException(getId());
    }
}
