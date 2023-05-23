package com.es.phoneshop.dao.impl;

import com.es.phoneshop.FunctionalReadWriteLock;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.enums.SortingField;
import com.es.phoneshop.enums.SortingType;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.comparator.DescriptionAndPriceComparator;
import com.es.phoneshop.model.comparator.SearchComparator;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products;
    private final AtomicLong productId;
    private final FunctionalReadWriteLock lock;

    ArrayListProductDao() {
        products = new ArrayList<>();
        productId = new AtomicLong(0);
        lock = new FunctionalReadWriteLock();
    }

    public static ArrayListProductDao getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ArrayListProductDao INSTANCE = new ArrayListProductDao();
    }

    @Override
    public Product getProduct(Long id) {
        return lock.read(() -> {
            Optional.ofNullable(id)
                    .orElseThrow(() -> new IllegalArgumentException("It is impossible to find product with null ID"));
            return products.stream()
                    .filter(product -> product.getProductId().equals(id))
                    .findAny()
                    .orElseThrow(() -> new ProductNotFoundException("Product with ID = " + id + " Not Found"));
        });
    }

    @Override
    public List<Product> findProducts(String description, SortingField sortingField, SortingType sortingType) {
        return lock.read(() -> products.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .filter(product -> description == null || description.isEmpty()
                        || countFoundWords(description, product.getDescription()) > 0)
                .sorted(new SearchComparator(description))
                .sorted(new DescriptionAndPriceComparator(sortingField, sortingType))
                .collect(Collectors.toList()));
    }

    private int countFoundWords(String description, String productDescription) {
        return (int) Stream.of(description.split("[^A-Za-z0-9I]+"))
                .distinct()
                .filter(productDescription::contains)
                .count();
    }

    @Override
    public void save(Product product) {
        lock.write(() -> {
            Optional.ofNullable(product)
                    .orElseThrow(() -> new IllegalArgumentException("Product equals null"));
            Optional.ofNullable(product.getProductId())
                    .ifPresentOrElse(
                            (id) -> {
                                Product foundProduct = getProduct(product.getProductId());
                                products.set(products.indexOf(foundProduct), product);
                            },
                            () -> {
                                product.setProductId(productId.incrementAndGet());
                                products.add(product);
                            });
        });
    }

    @Override
    public void delete(Long id) {
        lock.write(() -> {
            products.removeIf(product -> product.getProductId().equals(id));
        });
    }
}
