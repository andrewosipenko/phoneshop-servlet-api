package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.entity.sortParams.SortField;
import com.es.phoneshop.model.entity.sortParams.SortOrder;
import com.es.phoneshop.model.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.entity.product.Product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws ProductNotFoundException;
    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);
    void save(Product product);
    void delete(Long id);
    int getAmountOfProducts();
}
