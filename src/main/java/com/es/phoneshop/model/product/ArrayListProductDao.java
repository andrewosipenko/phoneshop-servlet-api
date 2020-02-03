package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static final List<Product> products = new ArrayList<>();
    @Override
    public Product getProduct(Long id) {
        return products.stream()
                .filter((p) -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No products with such id."));
    }

    @Override
    public List<Product> findProducts() {
        return products.stream()
                .filter(p-> p.getPrice()!=null && p.getStock()>0)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        if (product!=null && !products.contains(product))
            products.add(product);
    }

    @Override
    public void delete(Long id) {
        Product product=products.stream()
                .filter(p-> p.getId()==id)
                .findAny()
                .orElse(null);
        if (product!=null)
            products.remove(product);
    }
}
