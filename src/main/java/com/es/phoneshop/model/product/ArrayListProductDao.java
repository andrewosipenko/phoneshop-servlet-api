package com.es.phoneshop.model.product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products;

    public ArrayListProductDao(List<Product> products) {
        this.products = products;
    }


    public ArrayListProductDao() {
    }

    @Override
    public synchronized Product getProduct(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public synchronized List<Product> findProducts() {
        return products.stream()
                .filter(product -> product.getStock() > 0)
                .filter(product -> product.getPrice() != null)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        Optional<Product> existProduct = Optional.ofNullable(getProduct(product.getId()));
        if (existProduct.isPresent()) {
            update(product, existProduct.get());
        } else {
            product.setId(Integer.toUnsignedLong(products.size()));
            products.add(product);
        }
    }


    private void update(Product updateProduct, Product oldProduct) {
        oldProduct.setCode(updateProduct.getCode());
        oldProduct.setCurrency(updateProduct.getCurrency());
        oldProduct.setDescription(updateProduct.getDescription());
        oldProduct.setImageUrl(updateProduct.getImageUrl());
        oldProduct.setPrice(updateProduct.getPrice());
        oldProduct.setStock(updateProduct.getStock());
    }

    @Override
    public synchronized void delete(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }
}
