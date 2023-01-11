package com.es.phoneshop.model.product.dao.impl;

import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.model.product.enums.SortField;
import com.es.phoneshop.model.product.enums.SortOrder;
import com.es.phoneshop.model.product.exception.ProductNotFoundException;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
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

    private List<Product> products;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    private long currentMaxId = 1;


    private ArrayListProductDao() {
        products = new ArrayList<>();
    }

    @Override
    public Product getProduct(Long id) throws ProductNotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("Parameter id is null");
        }
        lock.readLock().lock();
        try {
            Product product = products.stream().
                    filter(currProduct -> id.equals(currProduct.getId())).
                    findAny().
                    orElseThrow(() -> new ProductNotFoundException(String.format("No such product with given code:%d", id)));
            return product;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Product> findProducts(String query, SortField sort, SortOrder order) {
        lock.readLock().lock();
        try {
            List<Product> foundProducts = products.stream().
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
    public void save(Product product) {
        lock.writeLock().lock();
        try {
            if (product.getId() != null) {
                Optional<Product> productToAdd = products.stream().
                        filter(currProduct -> currProduct.getId().equals(product.getId())).
                        findAny();
                if (productToAdd.isEmpty()) {
                    addNewProduct(product, products);
                } else {
                    products.set(products.indexOf(productToAdd.get()), product);
                }
            } else {
                addNewProduct(product, products);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Parameter id is null");
        }
        lock.writeLock().lock();
        try {
            if (!products.removeIf(product -> id.equals(product.getId()))) {
                throw new ProductNotFoundException(String.format("Cannot delete product with given id:%d", id));
            }
        } finally {
            lock.writeLock().unlock();
        }
    }


    private void addNewProduct(Product product, List<Product> products) {
        product.setId(currentMaxId++);
        products.add(product);
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
