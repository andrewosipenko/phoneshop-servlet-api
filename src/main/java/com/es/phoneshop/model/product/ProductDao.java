package com.es.phoneshop.model.product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);
    List<Product> findProducts();
    void save(Product product);
    void delete(Long id);
    List<Product> findProductsByDescription(String query);
    List<Product> sort(List<Product> unsortedProducts, String productField, String order);
}
