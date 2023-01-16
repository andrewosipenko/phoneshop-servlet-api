package com.es.phoneshop.model.product;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao implements ProductDao {
    private static ProductDao instance;

    public static synchronized ProductDao getInstance() {
        if(instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }
    private long maxId;
    private List<Product> products;

    private ArrayListProductDao() {
        this.maxId = 1L;
        this.products = new ArrayList<>();
    }

    @Override
    public synchronized Product getProduct(Long id) throws ProductNotFoundException {
        if (id == null) {throw new ProductNotFoundException(null);
        }

        return products.stream()
                .filter(product -> id.equals(product.getId()))
                .findAny()
                .orElseThrow(new ProductNotFoundException(id));
    }

    @Override
    public synchronized List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        Stream<Product> unsortedDesiredProductsStream =
                products.stream()
                        .filter(product -> (
                                query == null || query.trim().isEmpty()
                                        || containsAnyQueryWords(product.getDescription().toLowerCase(), query))
                        )
                        .filter(product -> product.getPrice() != null)
                        .filter(product -> product.getStock() > 0);

        Comparator<Product> productComparator;
        if (sortField == null) {
            if (query != null && !query.trim().isEmpty()) {
                productComparator = sortByRelevance(query);
            } else {
                /* sorting by default */
                productComparator = sortBySortOrderAndSortField(SortField.price, SortOrder.desc);
            }
        } else {
            productComparator = sortBySortOrderAndSortField(sortField, sortOrder);
        }

        return unsortedDesiredProductsStream.sorted(productComparator).collect(Collectors.toList());
    }

    private Comparator<Product> sortByRelevance(String query) {
        String[] queryWords = query.toLowerCase().split("\\s+");
        return Comparator.comparing(product ->
                productSearchRelevance(product.getDescription().toLowerCase(), queryWords));
    }

    private Comparator<Product> sortBySortOrderAndSortField(SortField sortField, SortOrder sortOrder) {
        Comparator<Product> productComparator = Comparator.comparing(product -> {
            switch (sortField) {
                case description:
                    return (Comparable) product.getDescription();
                case price:
                    return (Comparable) product.getPrice();
            }
            return null;
        });

        Comparator<Product> comparator = null;
        switch (sortOrder) {
            case asc:
                comparator = productComparator;
                break;
            case desc:
                comparator = productComparator.reversed();
                break;
        }

        return comparator;
    }

    private boolean containsAnyQueryWords(String description, String query) {
        return Arrays.stream(query.toLowerCase().split("\\s+"))
                .anyMatch(description::contains);
    }

    private double productSearchRelevance(String description, String[] queryWords) {
        return Arrays.stream(queryWords)
                .filter(description::contains)
                .count()
                /
                Double.valueOf(description.split(" ").length);
    }

    @Override
    public synchronized void save(Product product) {
        try {
            Product productToUpdate = getProduct(product.getId());
            product.setPriceHistoryList(productToUpdate.getPriceHistoryList());
            if(product.getPriceHistoryList()
                    .stream()
                    .noneMatch(p -> Objects.equals(p.getPrice(), product.getPrice())))
            {
                product.getPriceHistoryList()
                        .add(new PriceHistory(LocalDateTime.now(), product.getPrice(), product.getCurrency()));
            }
            products.remove(productToUpdate);
        } catch (ProductNotFoundException e) {
            product.setId(maxId++);
        } finally {
            products.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) throws ProductNotFoundException {
        Product product = getProduct(id);
        products.remove(product);
    }
}
