package com.es.phoneshop.service;

import com.es.phoneshop.model.product.Product;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class RecentlyViewedProductsServiceImpl implements RecentlyViewedProductsService {

    static final int MAX_RECENTLY_VIEWED_PRODUCTS = 3;
    private static RecentlyViewedProductsServiceImpl instance;

    private RecentlyViewedProductsServiceImpl() {

    }

    public static synchronized RecentlyViewedProductsServiceImpl getInstance() {
        if (instance == null) {
            instance = new RecentlyViewedProductsServiceImpl();
        }
        return instance;
    }

    @Override
    public synchronized void addRecentlyViewedProduct(HttpSession session, Product product) {
        List<Product> recentlyViewedProducts = getRecentlyViewedProducts(session);
        recentlyViewedProducts.remove(product);
        recentlyViewedProducts.add(0, product);

        if (recentlyViewedProducts.size() > MAX_RECENTLY_VIEWED_PRODUCTS) {
            recentlyViewedProducts.remove(MAX_RECENTLY_VIEWED_PRODUCTS);
        }

        session.setAttribute("recentlyViewedProducts", recentlyViewedProducts);
    }

    @Override
    public List<Product> getRecentlyViewedProducts(HttpSession session) {
        List<Product> recentlyViewedProducts = (List<Product>) session.getAttribute("recentlyViewedProducts");
        if (recentlyViewedProducts == null) {
            recentlyViewedProducts = new ArrayList<>();
            session.setAttribute("recentlyViewedProducts", recentlyViewedProducts);
        }
        return recentlyViewedProducts;
    }
}
