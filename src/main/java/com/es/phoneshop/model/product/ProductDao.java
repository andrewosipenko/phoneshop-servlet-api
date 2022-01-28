package com.es.phoneshop.model.product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<Product> getProduct(Long id);
    List<Product> findProducts(String query, SortField sortFiels, SortType sortType);
    void save(Product product);
    void delete(Long id);
}
