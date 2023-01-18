package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ProductDao instance;

    public static synchronized ProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    private static List<Product> products;
    private long maxId;

    private ArrayListProductDao() {
        this.products = new ArrayList<>();
    }

    @Override
    public synchronized Product getProduct(Long id) throws ProductNotFoundException {
        return products.stream()
                .filter(product -> id.equals(product.getId()))
                .findAny()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public synchronized List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        Comparator<Product> comparator = getProductComparator(sortField);
        List<Product> findProducts;

        if (query == null || query.isEmpty()) {
            findProducts = products.stream()
                    .filter(product -> product.getPrice() != null)
                    .filter(product -> product.getStock() > 0)
                    .collect(Collectors.toList());
        } else {
            String[] queryWords = query.split(" ");
            findProducts = products.stream()
                    .filter(product -> Arrays.stream(queryWords).anyMatch(queryWord -> product.getDescription().contains(queryWord)))
                    .filter(product -> product.getPrice() != null)
                    .filter(product -> product.getStock() > 0)
                    .sorted((product1, product2) -> (int) (Arrays.stream(queryWords).
                            filter(queryWord -> product2.getDescription().contains(queryWord))
                            .count() - Arrays.stream(queryWords)
                            .filter(queryWord -> product1.getDescription().contains(queryWord))
                            .count()))
                    .collect(Collectors.toList());
        }

        if (sortField == null) {
            return findProducts;
        } else {
            return findProducts.stream()
                    .sorted(sortOrder == SortOrder.asc ? comparator : comparator.reversed())
                    .collect(Collectors.toList());
        }
    }

    private static Comparator<Product> getProductComparator(SortField sortField) {
        Comparator<Product> comparator = Comparator.comparing(product -> {
            if (SortField.description == sortField) {
                return (Comparable) product.getDescription();
            } else {
                return (Comparable) product.getPrice();
            }
        });
        return comparator;
    }

    @Override
    public synchronized void save(Product product) throws NoSuchElementException {
        Product newProduct = products.stream().
                filter(product1 -> product1.getId().equals(product.getId())).
                findAny().
                orElse(null);
        if (newProduct == null) {
            product.setId(maxId++);
            products.add(product);
        } else if (product.getId() != null) {
            products.set(products.indexOf(newProduct), product);
        }
    }

    @Override
    public synchronized void delete(Long id) throws NoSuchElementException {
        products.remove(getProduct(id));
    }
}