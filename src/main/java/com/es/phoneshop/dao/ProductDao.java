package com.es.phoneshop.dao;

import com.es.phoneshop.model.Product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);

    List<Product> findProducts();

    void save(Product product);

    void delete(Long id);
}
