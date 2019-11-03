package com.es.phoneshop.model.product;

import java.util.*;
import java.util.stream.Collectors;

public class ProductListServise {
    private ProductDao productDao = new ArrayListProductDao();

    public Product getProduct(Long id) {
        return productDao.getProduct(id);
    }

    public List<Product> search(String query) {
        if (query == null) {
            return productDao.findProducts();
        } else {
            String[] allWordsFromQuery = query.toLowerCase().split("\\s+");
            return productDao.findProducts().stream()
                    .collect(Collectors.toMap(product -> product, product -> Arrays.stream(allWordsFromQuery)
                    .filter(word -> product.getDescription().toLowerCase().contains(word)).count()))
                    .entrySet().stream()
                    .filter(map -> map.getValue() > 0)
                    .sorted(Comparator.comparing(Map.Entry<Product, Long>::getValue).reversed())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
    }

    public List<Product> findProducts(String query, String sortField, String sortOrder) {
        String sortOptions = sortField + "_" + sortOrder;
        Comparator<Product> comparator = SortOptions.getComparator(sortOptions);

        if (comparator == null) {
            return search(query);
        } else {
            return search(query).stream().sorted(comparator).collect(Collectors.toList());
        }
    }

    public void save(Product product) {
        productDao.save(product);
    }

    public void delete(Long id) {
        productDao.delete(id);
    }
}
