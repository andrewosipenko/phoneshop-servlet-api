package com.es.phoneshop.model.product;

import java.util.List;

public interface ProductService {

    Product getProduct(Long id);

    List<Product> findProducts();

    void save(Product product);

    void delete(Long id);
}
