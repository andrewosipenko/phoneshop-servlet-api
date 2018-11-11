package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    List<Product> products = new ArrayList<>();

    public ArrayListProductDao(){}
    public ArrayListProductDao(List<Product> products) {
        this.products = products;
    }

    @Override
    public Product getProduct(Long id) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public List<Product> findProducts() {
        return this.products.stream().filter(x -> (x.getPrice() != null && x.getStock() > 0)).collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void delete(Long id) {
        throw new RuntimeException("Not implemented");
    }
}
