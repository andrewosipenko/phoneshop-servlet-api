package com.es.phoneshop.model.product;

import java.util.List;

public interface ProductService {

    Product getProduct(Long id);

    Product getProduct(String pathInfo);

    List<Product> findProducts();

    void save(Product product);

    void delete(Long id);

    List<Product> findProducts(String sort, String order, String query);
}
