package com.es.phoneshop.model.product;

import com.es.phoneshop.SortItems.SortField;
import com.es.phoneshop.SortItems.SortOrder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.es.phoneshop.SortItems.SortOrder.desc;
import static java.util.Comparator.comparing;

public class ArrayListProductDao implements ProductDao {
    private static ArrayListProductDao instance = new ArrayListProductDao();
    private static Long maxID = 0L;

    private List<Product> products;

    public static ArrayListProductDao getInstance() {
        return instance;
    }

    private ArrayListProductDao() {
        maxID = 0L;
        products = new ArrayList<>();
    }

    public synchronized void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public synchronized Product getProduct(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Element with id " + id + " is not found"));
    }

    @Override
    public synchronized List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        Stream<Product> stream = products.stream()
                .filter(x -> x.getPrice() != null && x.getStock() > 0);
        if (query != null) {
            stream = stream.filter(x -> x.getDescription().toLowerCase().contains(query.toLowerCase()));
        }
        if (sortField != null)
            stream = stream.sorted(getSortComparator(sortField, sortOrder));
        return stream.collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (!containsID(product.getId())) {
            products.add(product);
        } else {
            throw new IllegalArgumentException("Product is already in the list");
        }
    }

    @Override
    public synchronized void delete(Long id) {
        products.remove(getProduct(id));
    }

    private Comparator<Product> getSortComparator(SortField sortField, SortOrder sortOrder) {
        Comparator<Product> comparator = null;
        switch (sortField) {
            case price:
                comparator = comparing(Product::getPrice);
                break;
            case description:
                comparator = comparing(Product::getDescription);
                break;
        }
        if (sortOrder == desc) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

    private boolean containsID(Long id) {
        return products.stream()
                .anyMatch(product -> product.getId().equals(id));
    }

    private Long getNextId() {
        return ++maxID;
    }

}