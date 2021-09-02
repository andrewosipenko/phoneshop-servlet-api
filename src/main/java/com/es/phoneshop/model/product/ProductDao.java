package com.es.phoneshop.model.product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws NoElementsFoundException, IdIsNotValidException;
    List<Product> findProducts();
    void save(Product product) throws ProductIsNullException;
    void delete(Long id) throws IdIsNotValidException;
}
