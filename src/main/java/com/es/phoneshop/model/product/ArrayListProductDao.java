package com.es.phoneshop.model.product;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ProductDao instance;
    private static final Object mutex = new Object();
    private long maxId;
    private final List<Product> products;
    private final ReadWriteLock rwLock;

    public static ProductDao getInstance() {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new ArrayListProductDao();
                }
            }
        }
        return instance;
    }

    private ArrayListProductDao() {
        products = new ArrayList<>();
        rwLock = new ReentrantReadWriteLock();
    }

    @Override
    public Optional<Product> getProduct(Long id) {
        if (id == null) {
            return Optional.empty();
        } else {
            rwLock.readLock().lock();
            try {
                return products.stream()
                        .filter(product -> id.equals(product.getId()))
                        .findAny();
            } finally {
                rwLock.readLock().unlock();
            }
        }
    }

    private boolean isProductSearchResult(Product product, String query) {
        if (query == null || query.isEmpty()) {
            return true;
        }
        String[] tokens = query.split(" ");
        long result;
        result = Arrays.stream(tokens)
                .filter(item -> product.getDescription().toLowerCase().contains(item.toLowerCase()))
                .count();
        return result > 0;
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        rwLock.readLock().lock();
        try {
            Comparator<Product> comparator = new ProductSortByFieldComparator(sortField);
            if (SortOrder.desc == sortOrder) {
                comparator = comparator.reversed();
            }
            return products.stream()
                    .sorted(new ProductNaturalOrderComparator())
                    .filter(product -> isProductSearchResult(product, query))
                    .filter(product -> product.getPrice() != null)
                    .filter(product -> product.getStock() > 0)
                    .sorted(new ProductSearchComparator(query))
                    .sorted(comparator)
                    .collect(Collectors.toList());
        } finally {
            rwLock.readLock().unlock();
        }
    }


    @Override
    public void save(Product product) {
        if (product != null) {
            rwLock.writeLock().lock();
            try {
                if (product.getId() != null) {
                    for (int i = 0; i < products.size(); i++) {
                        if (product.getId().equals(products.get(i).getId())) {
                            products.set(i, product);
                            break;
                        }
                    }
                } else {
                    product.setId(maxId++);
                    products.add(product);
                }
            } finally {
                rwLock.writeLock().unlock();
            }
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            rwLock.writeLock().lock();
            try {
                products.removeIf(item -> id.equals(item.getId()));
            } finally {
                rwLock.writeLock().unlock();
            }
        }
    }
}
