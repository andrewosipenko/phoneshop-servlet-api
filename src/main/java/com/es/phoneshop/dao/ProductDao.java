package com.es.phoneshop.dao;

import com.es.phoneshop.dao.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws ProductNotFoundException;
    List<Product> findProducts();
    void save(Product product);
    void delete(Long id) throws ProductNotFoundException;
}
