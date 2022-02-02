package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ProductDao instance;

    public static synchronized ProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    private final List<Product> products;

    private long maxId = 0;

    ReadWriteLock lock = new ReentrantReadWriteLock();

    private ArrayListProductDao() {
        this.products = new ArrayList<>();
    }

    @Override
    public Optional<Product> getProduct(Long id) throws NoSuchElementException {
        if (id == null) {
            return Optional.empty();
        }
        lock.readLock().lock();
        try {
            return products.stream()
                    .filter(product -> id.equals(product.getId()))
                    .findAny();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField, SortType sortType) {
        String[] queryWords = new String[0];
        if (query != null) {
            queryWords = query.split(" ");
        }
        final ArrayList<String> queryWordsList = new ArrayList<>();
        Collections.addAll(queryWordsList, queryWords);
        lock.readLock().lock();
        try {
            return products
                    .stream()
                    .filter(new ProductStockPricePredicate().and(new ProductSearchPredicate(queryWordsList)))
                    .sorted(new ProductSortFieldComparator(sortType, sortField)
                            .thenComparing(new ProductSearchComparator(queryWordsList)))
                    .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void save(Product product) {
        if (product == null) {
            return;
        }
        lock.writeLock().lock();
        try {
            if (product.getId() != null) {
                products.forEach(product1 -> {
                    if (product1.getId().equals(product.getId())) {
                        products.set(products.indexOf(product1), product);
                    }
                });
            } else {
                product.setId(maxId++);
                products.add(product);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(Long id) {
        lock.writeLock().lock();
        try {
            products.removeIf(product -> id.equals(product.getId()));
        } finally {
            lock.writeLock().unlock();
        }
    }

}