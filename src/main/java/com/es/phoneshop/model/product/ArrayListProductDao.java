package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    List<Product> products;

    public ArrayListProductDao() {
        products = new ArrayList<>();
    }


    @Override
    public Product getProduct(Long id) {
        return products.stream().
                filter(product -> product.getId().equals(id)).
                findAny().orElseThrow( () -> new RuntimeException( "Product is not found"));
    }

    @Override
    public List<Product> findProducts() {
        return products.stream().
                filter(product -> product.getPrice() != null && product.getStock() > 0).
                collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product can not be null");
        }
        else {
            products.add(product);
        }
    }

    @Override
    public void delete(Long id) {
        products.remove(getProduct(id));
    }
}
