package com.es.phoneshop.model.product.dao.product;

import com.es.phoneshop.model.product.enums.SortBy;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductDao {

    Product getProduct(Long id) throws ProductNotFoundException;

    List<Product> findProducts();

    List<Product> findProducts(String query);

    void save(Product product);

    void delete(Long id) throws ProductNotFoundException;

    List<Product> findProducts(String query, SortBy field, boolean ascending);
}
