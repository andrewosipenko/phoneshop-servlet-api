package com.es.phoneshop.model;

import java.util.List;
import java.util.NoSuchElementException;

public interface ProductDao {
    List<Product> findProducts();
    Product getProduct(Long id) throws NoSuchElementException;
    void save(Product product);
    void remove(Long id);
    void generateID(Product product);
}
