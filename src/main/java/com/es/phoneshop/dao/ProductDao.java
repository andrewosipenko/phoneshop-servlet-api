package com.es.phoneshop.dao;

import com.es.phoneshop.dao.sorting.SortField;
import com.es.phoneshop.dao.sorting.SortOrder;
import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);
    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);
    void save(Product product);
    void delete(Long id);
}
