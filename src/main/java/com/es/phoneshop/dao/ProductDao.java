package com.es.phoneshop.dao;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.enums.SortField;
import com.es.phoneshop.model.enums.SortOrder;

import java.util.List;

public interface ProductDao extends Dao<Product> {
    List<Product> findProducts(String query, SortField field, SortOrder order);
}
