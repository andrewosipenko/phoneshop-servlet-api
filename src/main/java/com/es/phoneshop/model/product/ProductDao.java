package com.es.phoneshop.model.product;

import com.es.phoneshop.SortItems.SortField;
import com.es.phoneshop.SortItems.SortOrder;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);

    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);

    void save(Product product);

    void delete(Long id);
}
