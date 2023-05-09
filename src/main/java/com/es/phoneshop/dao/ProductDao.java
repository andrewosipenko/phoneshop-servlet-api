package com.es.phoneshop.dao;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<Product> getProduct(long id);
    List<Product> findProducts(String query, String sortField, String sortOrder);
    List<Product> findProducts();
    void save(Product product);
    void delete(long id) throws ProductNotFoundException;
}
