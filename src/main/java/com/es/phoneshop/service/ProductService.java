package com.es.phoneshop.service;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getProduct(long id);
    List<Product> findProducts();
    void save(Product product);
    void delete(long id) throws ProductNotFoundException;
}
