package com.es.phoneshop.model;

import java.util.List;

public interface ProductDao {
    List<Product> findProducts();
    Product getProduct(Long id);
    void save(Product product);
    boolean remove(Long id);
}
