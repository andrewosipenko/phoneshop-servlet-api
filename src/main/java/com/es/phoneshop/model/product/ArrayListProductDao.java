package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products;

    public ArrayListProductDao() {
        products = new ArrayList<>();
    }

    @Override
    public Product getProduct(Long id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Product> findProducts() {
        return products.stream().filter(product -> product.getPrice() != null && product.getStock() > 0).collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        if (product != null && products.stream().noneMatch(element -> element.getId().equals(product.getId()))) {
            products.add(product);
        }
    }

    @Override
    public void delete(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }
}
