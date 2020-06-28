package com.es.phoneshop.services.impl;

import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ProductDao;
import com.es.phoneshop.services.RecentlyViewedService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class RecentlyViewedServiceImpl implements RecentlyViewedService {
    private ProductDao productDao;
    private final Integer MAX_COUNT = 3;
    private final String RECENTLY_VIEWED_KEY = "recentlyViewedProducts";
    private static RecentlyViewedService recentlyViewedService;

    public RecentlyViewedServiceImpl() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static RecentlyViewedService getInstance() {
        if (recentlyViewedService == null) {
            recentlyViewedService = new RecentlyViewedServiceImpl();
        }
        return recentlyViewedService;
    }

    @Override
    public void add(Long productId, HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        List<Product> recentlyViewedProducts = getViewedProducts(request);
        Product product = productDao.getProduct(productId);

        if (recentlyViewedProducts.contains(product)) {
            recentlyViewedProducts.remove(product);
        } else if (recentlyViewedProducts.size() == MAX_COUNT) {
            recentlyViewedProducts.remove(MAX_COUNT - 1);
        }
        recentlyViewedProducts.add(0, product);
        httpSession.setAttribute(RECENTLY_VIEWED_KEY, recentlyViewedProducts);
    }

    @Override
    public List<Product> getViewedProducts(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        List<Product> recentlyViewedProducts = (List<Product>) httpSession.getAttribute(RECENTLY_VIEWED_KEY);
        if (recentlyViewedProducts == null) {
            recentlyViewedProducts = new ArrayList<Product>();
        }
        return recentlyViewedProducts;
    }
}
