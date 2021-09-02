package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.ProductNotFindException;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws ProductNotFindException;
    List<Product> findProducts();
    void save(Product product);
    void delete(Long id);
    void update(Product product);
}
