package com.es.phoneshop.model.product;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao implements ProductDao {

    private static ArrayListProductDao instance;

    public static synchronized ArrayListProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    private List<Product> products;

    private long maxProductId;

    private final Object lock = new Object();

    private ArrayListProductDao() {
        products = new ArrayList<>();
        maxProductId = 0;
    }

    @Override
    public Product getProduct(Long id) throws NoSuchElementException {
        synchronized (lock) {
            return products.stream()
                    .filter(product -> id != null && id.equals(product.getId()))
                    .findAny()
                    .orElseThrow(() -> new NoSuchElementException(String.valueOf(id)));

        }
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        synchronized (lock) {
            List<String> splitQuery = splitQuery(query);
            return products.stream().filter(product -> product.getPrice() != null)
                    .filter(product -> product.getStock() > 0)
                    .filter(product -> splitQuery.isEmpty() || splitQuery.stream().anyMatch(product.getDescription().toLowerCase()::contains))
                    .sorted(sortField == null ? generateComparatorForQuery(splitQuery) : generateComparatorForFieldAndOrder(sortField, sortOrder))
                    .collect(Collectors.toList());
        }
    }

    private Comparator<Product> generateComparatorForQuery(List<String> splitQuery) {
        Comparator<Product> comparator = Comparator
                .comparing(product -> splitQuery.size() - splitQuery.stream()
                        .filter(product.getDescription().toLowerCase()::contains)
                        .count());
        for (String query : splitQuery) {
            comparator = comparator.thenComparing(product -> !product.getDescription().toLowerCase().contains(query));
        }
        return comparator.thenComparing(product -> product.getDescription().length());
    }

    private List<String> splitQuery(String query) {
        synchronized (lock) {
            if (query == null) {
                return new ArrayList<>();
            }
            query.replaceAll("\\s+", " ").toLowerCase();
            return Stream.of(query.split(" "))
                    .map(String::new)
                    .collect(Collectors.toList());
        }
    }

    private Comparator<Product> generateComparatorForFieldAndOrder(SortField sortField, SortOrder sortOrder) {
        Comparator<Product> comparator = Comparator.comparing(product -> {
            if (sortField == SortField.description) {
                return (Comparable) product.getDescription();
            } else {
                return (Comparable) product.getPrice();
            }
        });
        if (sortOrder == SortOrder.desc) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

    @Override
    public void save(Product product) {
        synchronized (lock) {
            if (product == null) {
                throw new IllegalArgumentException();
            }
            if (product.getId() != null) {
                delete(product.getId());
                products.add(product);
            } else {
                product.setId(maxProductId++);
                products.add(product);
            }
        }
    }

    @Override
    public void delete(Long id) {
        synchronized (lock) {
            products.removeIf(product -> id != null && id.equals(product.getId()));
        }
    }

}
