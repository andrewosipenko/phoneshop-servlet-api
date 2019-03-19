package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private List<Product> products = new ArrayList<>();

    @Override
    public Product getProduct(Long id) {
        if (id == null) throw new NullPointerException("Id cant be null!");
        if (id < 0) throw new IllegalArgumentException("Id cant be negative!");
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .get();
    }

    @Override
    public List<Product> findProducts() {
        return products.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (product == null) throw new NullPointerException("Product cant be null!");
        if (products
                .stream()
                .anyMatch(p -> p.getId().equals(product.getId()))) {
            throw new IllegalArgumentException("Product with this id already exist!");
        }
        products.add(product);
    }

    @Override
    public synchronized void delete(Long id) {
        products.remove(getProduct(id));
    }
}
