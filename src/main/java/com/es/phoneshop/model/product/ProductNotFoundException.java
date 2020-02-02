package com.es.phoneshop.model.product;

public class ProductNotFoundException extends RuntimeException
{
    private Long id;

    public ProductNotFoundException(Long id)
    {
        this.id = id;
    }
}
