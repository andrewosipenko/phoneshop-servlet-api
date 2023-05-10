package com.es.phoneshop.service;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface ProductService {
    Product getProduct(long id);
    List<Product> findProducts();
    List<Product> findProducts(String query, String sortField, String sortOrder);
    void save(Product product);
    void delete(long id) throws ProductNotFoundException;
}
