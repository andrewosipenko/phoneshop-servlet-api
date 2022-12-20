package com.es.phoneshop.model.product;

import com.es.phoneshop.model.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws ProductNotFoundException;

    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);

    void save(Product product) throws ProductNotFoundException;

    void delete(Long id) throws ProductNotFoundException;
}
