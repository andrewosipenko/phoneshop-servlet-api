package com.es.phoneshop.model.product.dao.impl;

import com.es.phoneshop.model.product.dao.EntityDao;
import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.model.product.enums.SortField;
import com.es.phoneshop.model.product.enums.SortOrder;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao extends EntityDao<Product> implements ProductDao {
    private static volatile ProductDao instance;

    public static ProductDao getInstance() {
        ProductDao localInstance = instance;
        if (localInstance == null) {
            synchronized (ProductDao.class) {
                localInstance = instance;
                if (localInstance == null)
                    instance = localInstance = new ArrayListProductDao();
            }
        }
        return localInstance;
    }

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    private long currentMaxId = 1;


    private ArrayListProductDao() {
        entities = new ArrayList<>();
    }

    @Override
    public List<Product> findProducts(String query, SortField sort, SortOrder order) {
        lock.readLock().lock();
        try {
            List<Product> foundProducts = entities.stream().
                    filter(Objects::nonNull).
                    filter(product -> query == null || query.isEmpty() || containsAnyWordFrom(query, product.getDescription())).
                    sorted((p1, p2) -> {
                        if (query == null) {
                            return 0;
                        }
                        int firstProductMatches = numberOfWordsMatch(query, p1.getDescription());
                        int secondProductMatches = numberOfWordsMatch(query, p2.getDescription());
                        return secondProductMatches - firstProductMatches;
                    }).
                    filter(product -> product.getPrice() != null).
                    filter(product -> product.getStock() > 0).
                    collect(Collectors.toList());
            if (sort != null && order != null) {
                Comparator<Product> comparator = Comparator.comparing(product ->
                        chooseSortField((Product) product, sort)
                );
                if (SortOrder.ASC == order) {
                    comparator = comparator.thenComparing(Product::getDescription);
                } else {
                    comparator = comparator.thenComparing(Product::getDescription).reversed();
                }
                foundProducts = foundProducts.stream().sorted(comparator).collect(Collectors.toList());
            }
            return foundProducts;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    protected final void addNewEntity(Product entity, List<Product> entities) {
        entity.setId(currentMaxId++);
        entities.add(entity);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Parameter id is null");
        }
        lock.writeLock().lock();
        try {
            if (!entities.removeIf(product -> id.equals(product.getId()))) {
                throw new ProductNotFoundException(String.format("Cannot delete product with given id:%d", id));
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    private boolean containsAnyWordFrom(String query, String desc) {
        String[] words = query.split(" +");
        for (String word : words)
            if (desc.contains(word)) {
                return true;
            }
        return false;
    }

    private int numberOfWordsMatch(String query, String desc) {
        int counter = 0;
        String[] words = query.split(" +");
        for (String word : words)
            if (desc.contains(word)) {
                counter++;
            }
        return counter;
    }

    private Comparable chooseSortField(Product product, SortField sortField) {
        if (SortField.DESCRIPTION == sortField) {
            return product.getDescription();
        } else {
            return product.getPrice();
        }
    }
}
