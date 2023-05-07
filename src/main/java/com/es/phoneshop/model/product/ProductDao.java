package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.ProductNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<Product> getProduct(long id);
    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);
    void save(Product product);
    void delete(long id) throws ProductNotFoundException;
}
