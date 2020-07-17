package com.es.phoneshop.model.product;

import java.util.List;

public interface ProductService {

    Product getProduct(Long id);

    Product getProduct(String id);

    List<Product> findProducts();

    void save(Product product);

    void delete(Long id);

    List<Product> findProduct(String sort, String order, String query);
}
