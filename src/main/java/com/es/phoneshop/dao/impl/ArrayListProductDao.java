package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.GenericDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.enums.SearchType;
import com.es.phoneshop.enums.SortingField;
import com.es.phoneshop.enums.SortingType;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.comparator.DescriptionAndPriceComparator;
import com.es.phoneshop.model.comparator.SearchComparator;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListProductDao extends GenericDao<Product> implements ProductDao {
    ArrayListProductDao() {
        items = new ArrayList<>();
        id = 0L;
    }

    public static ArrayListProductDao getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ArrayListProductDao INSTANCE = new ArrayListProductDao();
    }

    @Override
    public Product getProduct(Long id) {
        try {
            return get(id);
        } catch (NoSuchElementException e) {
            throw new ProductNotFoundException("Product with ID = " + id + " Not Found");
        }
    }

    @Override
    public List<Product> findProducts(String description, SortingField sortingField, SortingType sortingType) {
        return lock.read(() -> items.stream()
                .filter(item -> item.getPrice() != null && item.getStock() > 0)
                .filter(item -> description == null || description.isEmpty()
                        || countFoundWords(description, item.getDescription()) > 0)
                .sorted(new SearchComparator(description))
                .sorted(new DescriptionAndPriceComparator(sortingField, sortingType))
                .collect(Collectors.toList()));
    }

    @Override
    public List<Product> advancedFindProducts(String description, BigDecimal minPrice, BigDecimal maxPrice
            , SearchType searchType) {
        return lock.read(() -> {
            List<Product> products = items.stream()
                    .filter(item -> description == null || description.isEmpty() || minPrice == null || maxPrice == null)
                    .collect(Collectors.toList());
            if (searchType == SearchType.ALL_WORDS) {
                return products.stream()
                        .filter(product -> product.getDescription().equals(description))
                        .filter(product -> product.getPrice().compareTo(minPrice) >= 0)
                        .filter(product -> product.getPrice().compareTo(maxPrice) <= 0)
                        .collect(Collectors.toList());
            } else {
                return items.stream()
                        .filter(product -> countFoundWords(description, product.getDescription()) > 0)
                        .filter(product -> product.getPrice().compareTo(minPrice) >= 0)
                        .filter(product -> product.getPrice().compareTo(maxPrice) <= 0)
                        .collect(Collectors.toList());
            }
        });
    }

    private int countFoundWords(String description, String productDescription) {
        return (int) Stream.of(description.split("[^A-Za-z0-9I]+"))
                .distinct()
                .filter(productDescription::contains)
                .count();
    }
}
