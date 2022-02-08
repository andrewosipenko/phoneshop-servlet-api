package com.es.phoneshop.model.product.service;

import com.es.phoneshop.model.product.bean.Product;
import com.es.phoneshop.model.product.bean.RecentViewCart;
import com.es.phoneshop.model.product.dao.ArrayListProductDao;

import javax.servlet.http.HttpSession;
import java.util.List;

public class RecentViewedServiceImpl implements RecentViewedService {
    private static final String RECENT_VIEW_SESSION_ATTRIBUTE = RecentViewedServiceImpl.class.getName() + ".recentView";
    private static volatile RecentViewedService instance;

    public static RecentViewedService getInstance() {
        RecentViewedService result = instance;
        if (result != null) {
            return result;
        }
        synchronized (RecentViewedService.class) {
            if (instance == null) {
                instance = new RecentViewedServiceImpl();
            }
            return instance;
        }
    }

    @Override
    public synchronized RecentViewCart getRecentViewed(HttpSession session) {
        RecentViewCart products = (RecentViewCart) session.getAttribute(RECENT_VIEW_SESSION_ATTRIBUTE);
        System.out.println(products);
        if (products == null) {
            products = new RecentViewCart();
            session.setAttribute(RECENT_VIEW_SESSION_ATTRIBUTE, products);
        }
        return products;
    }

    @Override
    public synchronized void add(Long productId, RecentViewCart recentViewCart) {
        Product product = ArrayListProductDao.getInstance().getProduct(productId);
        List<Product> recentProducts = recentViewCart.getItems();
        if (recentProducts.contains(product)) {
            recentProducts.remove(product);
        } else {
            if (recentProducts.size() >= 4) {
                recentProducts.remove(0);
            }
        }
        recentProducts.add(product);
    }
}
