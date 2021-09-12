package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.SortField;
import com.es.phoneshop.model.SortOrder;
import com.es.phoneshop.model.product.Product;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private long maxId;
    private final List<Product> products;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static ProductDao instance;

    public static synchronized ProductDao getInstance() {
        if (instance == null) instance = new ArrayListProductDao();
        return instance;
    }

    private ArrayListProductDao() {
        maxId = 0;
        products = new ArrayList<>();
    }

    @Override
    public Optional<Product> getProduct(Long id) {
        readWriteLock.readLock().lock();
        try {
            return products.stream()
                    .filter(product -> product.getId().equals(id) && product.getPrice() != null && product.getStock() > 0)
                    .findAny();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    private List<Product> getProductsInStock(List<Product> products) {
        return products.stream()
                .filter(product -> product.getPrice() != null && product.getStock() > 0)
                .collect(Collectors.toList());
    }

    private double compareByRelevancy(Product product, List<String> tokens) {
        long tokenNumber = tokens.stream().filter(token -> product.getDescription().contains(token)).count();
        return tokenNumber / (double) product.getDescription().split("\\s").length;
    }

    private List<Product> sortByQuery(String query, List<Product> products) {
        List<String> tokens = Arrays.asList(query.split("\\s").clone());
        Comparator<Product> comparator =
                Comparator.comparingDouble(product -> compareByRelevancy(product, tokens));
        return products.stream()
                .filter(product -> tokens.stream().anyMatch(token -> product.getDescription().contains(token)))
                .sorted(comparator.reversed())
                .collect(Collectors.toList());
    }

    private Comparator<Product> getComparatorBySortField(String sortField, String sortOrder) {
        Comparator<Product> comparator;
        if (SortField.DESCRIPTION.toString().toLowerCase().equals(sortField)) {
            comparator = Comparator.comparing(Product::getDescription);
        } else comparator = Comparator.comparing(Product::getPrice);
        if (SortOrder.DESC.toString().toLowerCase().equals(sortOrder)) return comparator.reversed();
        else return comparator;
    }

    private List<Product> sortByField(List<Product> products, String sortField, String sortOrder) {
        return products.stream()
                .sorted(getComparatorBySortField(sortField, sortOrder))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findProducts(String query, String sortField, String sortOrder) {
        readWriteLock.readLock().lock();
        List<Product> fetchedProducts;
        try {
            fetchedProducts = getProductsInStock(products);
            if (query != null && !query.isEmpty()) {
                fetchedProducts = sortByQuery(query, fetchedProducts);
            }
            if (sortField != null && !sortField.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
                fetchedProducts = sortByField(fetchedProducts, sortField, sortOrder);
            }
            return fetchedProducts;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public void save(Product product) {
        readWriteLock.writeLock().lock();
        try {
            Optional<Product> oldProduct = products.stream().filter(pr -> pr.getId().equals(product.getId())).findAny();
            if (oldProduct.isPresent()) {
                int index = products.indexOf(oldProduct.get());
                products.set(index, product);
            } else {
                product.setId(maxId++);
                products.add(product);
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void delete(Long id) {
        readWriteLock.writeLock().lock();
        try {
            products.removeIf(product -> product.getId().equals(id));
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
