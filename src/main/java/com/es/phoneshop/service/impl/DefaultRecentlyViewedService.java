package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.RecentlyViewedService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class DefaultRecentlyViewedService implements RecentlyViewedService {
    private static final String RECENTLY_VIEWED_ATTRIBUTE = "recentlyViewed_" + DefaultRecentlyViewedService.class;

    public static DefaultRecentlyViewedService getInstance() {
        return RecentlyViewedServiceHolder.instance;
    }

    private static class RecentlyViewedServiceHolder {
        private static DefaultRecentlyViewedService instance = new DefaultRecentlyViewedService();
    }

    @Override
    public List<Product> getProducts(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Product> recentlyViewed = (ArrayList<Product>) session.getAttribute(RECENTLY_VIEWED_ATTRIBUTE);
        if (recentlyViewed == null) {
            recentlyViewed = new ArrayList<>();
            session.setAttribute(RECENTLY_VIEWED_ATTRIBUTE, recentlyViewed);
        }
        return recentlyViewed;
    }

    @Override
    public void addProduct(HttpServletRequest request, Product product) {
        List<Product> recentlyViewed = getProducts(request);
        if (recentlyViewed.contains(product)) {
            recentlyViewed.remove(product);
        } else if (recentlyViewed.size() == 3) {
            recentlyViewed.remove(recentlyViewed.size() - 1);
        }
        recentlyViewed.add(0, product);
    }
}
