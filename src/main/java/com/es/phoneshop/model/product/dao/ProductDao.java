package com.es.phoneshop.model.product.dao;

import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws ProductNotFoundException;
    List<Product> findProducts();
    void save(Product product);
    void delete(Long id);
}
