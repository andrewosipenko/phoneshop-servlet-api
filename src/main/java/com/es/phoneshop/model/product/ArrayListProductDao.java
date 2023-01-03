package com.es.phoneshop.model.product;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ProductDao instance;
    private static final Object mutex = new Object();
    private long maxId;
    private final List<Product> products;
    private final SortingEnumMap sortingEnumMap;
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
        sortingEnumMap = new SortingEnumMap();
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
        return productRelevance(product, query) > 0;
    }

    private int productRelevance(Product product, String query) {
        int matches = 0;
        String[] tokens = query.split(" ");
        for (String queryToken : tokens) {
            if (product.getDescription().toLowerCase().contains(queryToken.toLowerCase())) {
                matches++;
            }
        }
        return matches;
    }

    private Comparator<Product> getComparator(String query, SortingParams sortingParams) {
        if (sortingParams == null) {
            return Comparator.<Product>comparingInt(p -> productRelevance(p, query)).reversed();
        } else {
            return sortingEnumMap.get(sortingParams);
        }
    }

    @Override
    public List<Product> findProducts(String query, SortingParams sortingParams) {
        rwLock.readLock().lock();
        try {
            Predicate<Product> isProductSearchPredicate = product -> isProductSearchResult(product, query);
            Predicate<Product> productIsInStockPredicate = product -> product.getStock() > 0;
            Predicate<Product> productHasPricePredicate = product -> product.getPrice() != null;

            if ((query == null || query.isEmpty()) &&
                    (sortingParams == null || sortingParams == SortingParams.defaultSort)) {
                return products.stream()
                        .filter(productIsInStockPredicate.and(productHasPricePredicate))
                        .collect(Collectors.toList());
            } else if (sortingParams == SortingParams.defaultSort) {
                return products.stream()
                        .filter(isProductSearchPredicate
                                .and(productIsInStockPredicate)
                                .and(productHasPricePredicate))
                        .collect(Collectors.toList());
            } else {
                return products.stream()
                        .filter(isProductSearchPredicate
                                .and(productIsInStockPredicate)
                                .and(productHasPricePredicate))
                        .sorted(getComparator(query, sortingParams))
                        .collect(Collectors.toList());
            }
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

    @Override
    public void clearProductDao() {
        products.clear();
        maxId = 0;
    }
}
