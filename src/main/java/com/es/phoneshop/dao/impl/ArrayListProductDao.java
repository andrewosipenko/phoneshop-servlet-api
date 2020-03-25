package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.enums.SortField;
import com.es.phoneshop.model.enums.SortOrder;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class ArrayListProductDao extends AbstractDao<Product> implements ProductDao {
    private ArrayListProductDao() {
        super.items = new ArrayList<>();
    }

    public static ArrayListProductDao getInstance() {
        return ProductDaoHolder.instance;
    }

    private static class ProductDaoHolder {
        private static final ArrayListProductDao instance = new ArrayListProductDao();
    }

    public List<Product> getProductList() {
        return super.items;
    }

    @Override
    public synchronized List<Product> findProducts(String query, SortField field, SortOrder order) {
        if (query == null && field == null) {
            return defaultFindProducts();
        } else if (field == null) {
            return findProductsWithQuery(query);
        } else if (query == null) {
            return sortProducts(defaultFindProducts(), field, order);
        } else {
            return sortProducts(findProductsWithQuery(query), field, order);
        }
    }

    private List<Product> defaultFindProducts() {
        return super.items.stream()
                .filter(product -> product.getPrice() != null)
                .filter(product -> product.getStock() > 0)
                .collect(Collectors.toList());
    }

    private List<Product> findProductsWithQuery(String query) {
        String[] words = query.toLowerCase().split(" ");

        Predicate<Product> hasMatch = product -> Arrays.stream(words)
                .anyMatch(word -> product.getDescription().toLowerCase().contains(word));

        ToIntFunction<Product> matchCount = product -> (int) Arrays.stream(words)
                .filter(word -> product.getDescription().toLowerCase().contains(word))
                .count();

        return defaultFindProducts().stream()
                .filter(hasMatch)
                .sorted(Comparator.comparingInt(matchCount).reversed())
                .collect(Collectors.toList());
    }


    private List<Product> sortProducts(List<Product> productList, SortField field, SortOrder order) {
        Comparator<Product> productComparator = null;
        switch (field) {
            case DESCRIPTION:
                productComparator = Comparator.comparing(Product::getDescription);
                break;
            case PRICE:
                productComparator = Comparator.comparing(Product::getPrice);
                break;
        }
        if (order == SortOrder.DESC) {
            productComparator = productComparator.reversed();
        }

        productList.sort(productComparator);
        return productList;
    }
}
