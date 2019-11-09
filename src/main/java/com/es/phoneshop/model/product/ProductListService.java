package com.es.phoneshop.model.product;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductListService {
    private ProductDao productDao = ArrayListProductDao.getInstance();

    public Product getProduct(Long id) {
        return productDao.getProduct(id);
    }

    public List<Product> search(String query) {
        if (query == null || query.equals("")) {
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

        if (sortOptions.equals("null_null")) {
            return search(query);
        } else {
            Comparator<Product> comparator = SortOptions.valueOf(sortOptions.toUpperCase());
            return search(query).stream().sorted(comparator).collect(Collectors.toList());
        }
    }

    public void save(Product product) {
        productDao.save(product);
    }

    public void delete(Long id) {
        productDao.delete(id);
    }

    public ProductDao getProductDao() {
        return productDao;
    }
}
