package com.es.phoneshop.model.product;

import com.es.phoneshop.exception.NoSuchProductWithCurrentIdException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products;

    public ArrayListProductDao() {
        products = new ArrayList<>();
    }

    @Override
    public Product getProduct(Long id) throws NoSuchProductWithCurrentIdException {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst().orElseThrow(NoSuchProductWithCurrentIdException::new);
    }

    @Override
    public List<Product> findProducts() {
        return products.stream().filter(p -> p.getStock() > 0 && p.getPrice() != null).collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        if (products.stream().noneMatch(p -> p.getId().equals(product.getId()))) {
            products.add(product);
        }
    }

    @Override
    public void delete(Long id) throws NoSuchProductWithCurrentIdException {
        Product productForDelete = products.stream().filter(p -> p.getId().equals(id)).findFirst().orElseThrow(NoSuchProductWithCurrentIdException::new);
        products.remove(productForDelete);
    }
}