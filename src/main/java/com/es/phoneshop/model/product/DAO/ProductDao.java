package com.es.phoneshop.model.product.DAO;

import com.es.phoneshop.model.product.entity.Product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);

    List<Product> findProducts();

    Product save(Product product);

    void delete(Long id);
}
