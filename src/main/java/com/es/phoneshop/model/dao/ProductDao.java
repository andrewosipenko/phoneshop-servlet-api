package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.entity.product.Product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws ProductNotFoundException;
    List<Product> findProducts();
    void save(Product product);
    void delete(Long id);
    int getAmountOfProducts();
}
