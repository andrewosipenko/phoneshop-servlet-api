package com.es.phoneshop.model.product.dao;

import com.es.phoneshop.model.DAO;
import com.es.phoneshop.model.product.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface ProductDao extends DAO<Product> {
    Optional<Product> getItem(Long id) throws NoSuchElementException;
    List<Product> getAll();
    void save(Product product);
    void delete(Long id);
    //
    List<Product> find(String query);

    List<Product> advancedSearch(String productCode, BigDecimal minPrice, BigDecimal maxPrice, Integer minStock);
}
