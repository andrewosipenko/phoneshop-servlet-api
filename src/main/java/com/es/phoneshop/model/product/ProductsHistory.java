package com.es.phoneshop.model.product;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ProductsHistory {
    private static ProductsHistory instance;

    private static final String SEARCH_HISTORY_SESSION_ATTRIBUTE =
            ProductsHistory.class.getName() + ".products";
    private ProductsHistory() {
    }

    public static ProductsHistory getInstance() {
        if (instance == null) {
            instance = new ProductsHistory();
        }
        return instance;
    }

    public synchronized void addRecentProduct(List<Product> products, Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).equals(product)) {
                products.remove(i);
            }
        }
        if (products.size() == 3) {
            products.remove(0);
            products.add(product);
        } else {
            products.add(product);
        }
    }

    public synchronized List<Product> getProducts(HttpServletRequest request) {
        List<Product> products = (List<Product>) request.getSession().getAttribute(SEARCH_HISTORY_SESSION_ATTRIBUTE);
        if (products == null) {
            products = new ArrayList<>();
            request.getSession().setAttribute(SEARCH_HISTORY_SESSION_ATTRIBUTE, products);
        }
        return products;
    }
}