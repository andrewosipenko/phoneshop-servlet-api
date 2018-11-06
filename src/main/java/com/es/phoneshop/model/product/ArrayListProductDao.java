package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    List<Product> products = new ArrayList<>();

    @Override
    public Product getProduct(Long id) {
        for(Product product : products) {
            if (id == product.getId()) {
                return product;
            }
        }
        return null; // exception?
        //return products.stream().filter(product -> id == product.getId()).findAny().orElse(null);
    }

    @Override
    public List<Product> findProducts() {
        return products.stream().filter(product -> product.getPrice() != null && product.getStock() > 0).collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public void delete(Long id) {
        for(Product product : products) {
            if (id == product.getId()) {
                products.remove(product);
            }
        }
    }
}
