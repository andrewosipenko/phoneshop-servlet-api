package com.es.phoneshop.model.product;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products;
    private long maxId;

    private static ArrayListProductDao instance;
    private ReadWriteLock rwLock;

    private ArrayListProductDao() {
        this.products = new ArrayList<>();
        this.rwLock = new ReentrantReadWriteLock();
    }

    public static ArrayListProductDao getInstance() {
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
    public Optional<Product> getProduct(final Long id) {
        Lock readLock = rwLock.readLock();
        readLock.lock();
        try {
            return products.stream()
                    .filter(product -> product.getId().equals(id))
                    .findAny();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Product> findProducts(@NonNull final String searchQuery,
                                      @NonNull final SortField sortField,
                                      @NonNull final SortOrder sortOrder) {
        Lock readLock = rwLock.readLock();
        readLock.lock();
        try {
            Comparator<Product> comparator = Comparator.comparing(product -> {
                if (sortField != null && SortField.description == sortField) {
                    return (Comparable) product.getDescription();
                } else {
                    return (Comparable) product.getPrice();
                }
            });

            if (sortOrder != null && SortOrder.desc == sortOrder) {
                comparator = comparator.reversed();
            }

            return List.copyOf(products.stream()
                    .filter((product -> searchQuery == null || searchQuery.isEmpty() ||
                            product.getDescription().toUpperCase().contains(searchQuery.toUpperCase())))
                    .filter(this::nonNullPrice)
                    .filter(this::productIsInStock)
                    .sorted(comparator)
                    .collect(Collectors.toList()));
        } finally {
            readLock.unlock();
        }
    }

    private boolean productIsInStock(@NonNull final Product product) {
        return product.getStock() > 0;
    }

    private boolean nonNullPrice(@NonNull final Product product) {
        return product.getPrice() != null;
    }

    @Override
    public void save(@NonNull final Product product) {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            if (product.getId() != null) {
                Optional<Product> optProduct = getProduct(product.getId());
                optProduct.ifPresent(products::remove);
            } else {
                product.setId(maxId++);
            }
            products.add(product);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void delete(final Long id) {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            Optional<Product> optProduct = products.stream()
                    .filter(product -> product.getId().equals(id))
                    .findAny();
            optProduct.ifPresent(products::remove);
        } finally {
            writeLock.unlock();
        }
    }

}


