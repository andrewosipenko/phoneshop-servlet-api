package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.entity.sortParams.SortField;
import com.es.phoneshop.model.entity.sortParams.SortOrder;
import com.es.phoneshop.model.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.entity.product.Product;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ProductDao instance;

    public static synchronized ProductDao getInstance() {
        if(instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    private List<Product> products;
    private long currId;
    private final Object lock = new Object();

    private ArrayListProductDao() {
        this.products = new ArrayList<>();
    }

    @Override
    public Product getProduct(Long id) {
        synchronized (lock) {
            if (id == null) {
                throw new IllegalArgumentException("Given id is null when using getProduct");
            }
            return products.stream()
                    .filter(product -> id.equals(product.getId()))
                    .findAny()
                    .orElseThrow(() -> new ProductNotFoundException(String.format("Couldn't find product with id: %d in getProduct", id)));
        }
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        synchronized (lock) {
            Comparator<Product> comparator = Comparator.comparing(product -> {
                if (sortField != null && SortField.description == sortField) {
                    return (Comparable) product.getDescription();
                }
                else {
                    return (Comparable) product.getPrice();
                }
            });

            List<Product> sortedProducts = (query == null || query.trim().isEmpty())
                    ? products : calculateSearchQueryInOrder(query);

            return sortedProducts.stream()
                    .filter(product -> product.getPrice() != null)
                    .filter(product -> product.getStock() > 0)
                    // if sortOrder == null -> using stable sort(same order), else -> sort with condition
                    .sorted(sortOrder == null ? ((a,b) -> 0 ) : (sortOrder == SortOrder.asc ? comparator : comparator.reversed()))
                    .collect(Collectors.toList());
        }
    }

    private List<Product> calculateSearchQueryInOrder(String query) {
        String[] words = query.trim().split("\\W+");
        Map<Product, Integer> frequencyOfProducts = new HashMap<>();

        for(String word : words) {
            products.stream()
                    .filter((product -> product.getDescription().contains(word)))
                    .forEach((product) -> {
                        frequencyOfProducts.compute(product, (k, v) -> v == null ? 1 : v + 1);
                    });
        }

        Map<Integer, List<Product>> sortedFrequency = new TreeMap<>();
        frequencyOfProducts.forEach((k,v) -> {
            if(!sortedFrequency.containsKey(v)) {
                sortedFrequency.put(v, new ArrayList<>());
            }
            sortedFrequency.get(v).add(k);
        });
        List<Product> sortedProducts = new ArrayList<>();

        sortedFrequency.forEach((k,v)->{
            sortedProducts.addAll(v);
        });
        Collections.reverse(sortedProducts);
        return sortedProducts;
    }
    @Override
    public void save(Product product) {
        synchronized (lock) {
            if(product.getId() != null) {
                products.set(Math.toIntExact(getProduct(product.getId()).getId()), product);
            }
            else {
                product.setId(currId++);
                products.add(product);
            }
        }
    }

    @Override
    public void delete(Long id) {
        synchronized (lock) {
            if (id == null) {
                throw new IllegalArgumentException("Given id is null when using delete");
            }
            if (!products.removeIf(currProduct -> id.equals(currProduct.getId()))) {
                    throw new ProductNotFoundException(String.format("Couldn't find product with id: %d delete", id));
            }
        }
    }

    public int getAmountOfProducts() {
        return products.size();
    }
}
