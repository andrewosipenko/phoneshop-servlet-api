package com.es.phoneshop.service;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getProduct(long id);
    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);
    void save(Product product);
    void delete(long id) throws ProductNotFoundException;
}
