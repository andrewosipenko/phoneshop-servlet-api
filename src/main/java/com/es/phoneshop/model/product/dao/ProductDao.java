package com.es.phoneshop.model.product.dao;

import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.model.product.enums.SortField;
import com.es.phoneshop.model.product.enums.SortOrder;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import java.util.List;

public interface ProductDao {
    Product getEntity(Long id, String message) throws ProductNotFoundException;

    List<Product> findProducts(String query, SortField sort, SortOrder order);

    void save(Product product);

    void delete(Long id);
}
