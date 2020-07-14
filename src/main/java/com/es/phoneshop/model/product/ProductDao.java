package com.es.phoneshop.model.product;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface ProductDao extends DAO<Product, Long> {
    Optional<Product> get(Long id) throws NoSuchElementException;
    List<Product> getAll();
    void save(Product product);
    void delete(Long id);
}
