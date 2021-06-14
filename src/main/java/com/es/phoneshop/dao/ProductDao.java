package com.es.phoneshop.dao;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.sortenum.SortField;
import com.es.phoneshop.model.sortenum.SortOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<Product> getProduct(Long id);

    List<Product> findProducts(String searchQuery, SortField sortField, SortOrder sortOrder);

    void save(Product product);

    void delete(Long id);

    void changePrice(Long id, BigDecimal price);
}
