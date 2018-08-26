package com.es.phoneshop.model;

import java.util.List;

public interface ProductDao {
    Product getProduct(long id);
    List<Product> findProducts();
    void save(Product product);
    void remove(long id);
}
