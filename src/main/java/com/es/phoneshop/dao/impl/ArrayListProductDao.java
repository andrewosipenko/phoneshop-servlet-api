package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.enums.SortField;
import com.es.phoneshop.dao.enums.SortOrder;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.Product;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static ProductDao instance;

    private final List<Product> products;
    private long currentId;
    private final ReadWriteLock locker;

    private ArrayListProductDao() {
        products = new ArrayList<>();
        locker = new ReentrantReadWriteLock();
    }

    public static ProductDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListProductDao.class) {
                if (instance == null) {
                    instance = new ArrayListProductDao();
                }
            }
        }
        return instance;
    }

    @Override
    public Product getProduct(Long id) throws ProductNotFoundException, IllegalArgumentException {
        locker.readLock().lock();
        try {
            if (id == null) {
                throw new IllegalArgumentException("Null id");
            }
            return products.stream()
                    .filter(product -> id.equals(product.getId()))
                    .findAny()
                    .orElseThrow(() -> new ProductNotFoundException("No product with id " + id, id));
        } finally {
            locker.readLock().unlock();
        }
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        locker.readLock().lock();
        try {
            List<Product> result = products;

            if (query != null && !query.isEmpty()) {
                result = products.stream()
                        .filter(product -> countMatchingWords(product, query) != 0)
                        .sorted(Comparator.comparingInt(product -> countMatchingWords((Product) product, query))
                                .reversed())
                        .collect(Collectors.toList());
            }

            if (sortField != null) {
                result = result.stream()
                        .sorted(getComparator(sortField, sortOrder))
                        .collect(Collectors.toList());
            }

            return result.stream()
                    .filter(this::productHasNonNullPrice)
                    .filter(this::productIsInStock)
                    .collect(Collectors.toList());
        } finally {
            locker.readLock().unlock();
        }
    }

    private Comparator<Product> getComparator(SortField sortField, SortOrder sortOrder) {
        Comparator<Product> comparator = (product1, product2) -> {
            if (SortField.DESCRIPTION == sortField) {
                return product1.getDescription().compareTo(product2.getDescription());
            } else {
                return product1.getPrice().compareTo(product2.getPrice());
            }
        };
        if (SortOrder.DESC == sortOrder) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

    private int countMatchingWords(Product product, String query) {
        String[] queryTerms = query.toLowerCase().split("\\s");
        String[] productDescriptionTerms = product.getDescription().toLowerCase().split("\\s");
        return (int) Arrays.stream(queryTerms)
                .filter(Arrays.asList(productDescriptionTerms)::contains)
                .count();
    }

    private boolean productHasNonNullPrice(Product product) {
        return product.getPrice() != null;
    }

    private boolean productIsInStock(Product product) {
        return product.getStock() > 0;
    }

    @Override
    public void save(Product product) throws ProductNotFoundException {
        locker.writeLock().lock();
        try {
            if (product.getId() == null) {
                product.setId(++currentId);
                products.add(product);
            } else {
                int index = products.indexOf(getProduct(product.getId()));
                products.set(index, product);
            }
        } finally {
            locker.writeLock().unlock();
        }
    }

    @Override
    public void delete(Long id) {
        locker.writeLock().lock();
        try {
            products.removeIf(product -> product.getId() == null || id.equals(product.getId()));
        } finally {
            locker.writeLock().unlock();
        }
    }
}
