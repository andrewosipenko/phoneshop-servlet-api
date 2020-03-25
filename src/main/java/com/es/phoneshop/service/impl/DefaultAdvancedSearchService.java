package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.AdvancedSearchService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultAdvancedSearchService implements AdvancedSearchService {
    private ArrayListProductDao productDao;
    Map<String, String> errors;

    private DefaultAdvancedSearchService() {
        productDao = ArrayListProductDao.getInstance();
        errors = new HashMap<>();
    }

    public static DefaultAdvancedSearchService getInstance() {
        return AdvancedSearchServiceHolder.instance;
    }

    private static class AdvancedSearchServiceHolder {
        private static final DefaultAdvancedSearchService instance = new DefaultAdvancedSearchService();
    }

    @Override
    public List<Product> advancedSearch(String query, String stringMinPrice, String stringMaxPrice,
                                        String stringMinStock, String stringMaxStock) {
        List<Product> products = productDao.findProducts(query, null, null);
        errors = new HashMap<>();
        BigDecimal minPrice = new BigDecimal(0);
        BigDecimal maxPrice = new BigDecimal(100000);
        int minStock = 0;
        int maxStock = 100000;
        try {
            minPrice = new BigDecimal(Integer.parseInt(stringMinPrice));
        } catch (NumberFormatException exception) {
            errors.put("errorMinPrice", "Not a number");
        }
        try {
            maxPrice = new BigDecimal(Integer.parseInt(stringMaxPrice));
        } catch (NumberFormatException exception) {
            errors.put("errorMaxPrice", "Not a number");
        }
        try {
            minStock = Integer.parseInt(stringMinStock);
        } catch (NumberFormatException exception) {
            errors.put("errorMinStock", "Not a number");
        }
        try {
            maxStock = Integer.parseInt(stringMaxStock);
        } catch (NumberFormatException exception) {
            errors.put("errorMaxStock", "Not a number");
        }

        BigDecimal finalMinPrice = minPrice;
        BigDecimal finalMaxPrice = maxPrice;
        int finalMinStock = minStock;
        int finalMaxStock = maxStock;
        return products.stream()
                .filter(product -> product.getPrice().compareTo(finalMinPrice) >= 0)
                .filter(product -> product.getPrice().compareTo(finalMaxPrice) <= 0)
                .filter(product -> product.getStock() >= finalMinStock)
                .filter(product -> product.getStock() <= finalMaxStock)
                .collect(Collectors.toList());
    }
}
