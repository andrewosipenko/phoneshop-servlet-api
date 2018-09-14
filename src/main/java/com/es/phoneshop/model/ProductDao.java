package com.es.phoneshop.model;

import java.util.List;

public interface ProductDao {
    List<Product> findProducts();
    Product getProduct(Long id);
    void save(Product product);
    void remove(Long id);
    void generateID(Product product);
}
