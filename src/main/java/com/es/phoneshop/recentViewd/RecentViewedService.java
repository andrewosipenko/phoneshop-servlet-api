package com.es.phoneshop.recentViewd;


import com.es.phoneshop.cart.DefaultCartService;
import com.es.phoneshop.lock.SessionLock;
import com.es.phoneshop.lock.SessionLockService;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class RecentViewedService implements RecentViewed {
    private static final String RECENTVIEWED_SESSION_ATTRIBUTE = RecentViewedService.class.getName() + ".recentViewed";
    private static RecentViewed instance;
    private static final Object mutex = new Object();
    private final SessionLock lock;
    private final int size;

    private RecentViewedService() {
        lock = SessionLockService.getInstance();
        size = 3;
    }

    public static RecentViewed getInstance() {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new RecentViewedService();
                }
            }
        }
        return instance;
    }

    public void addToRecentViewed(HttpServletRequest request, Product product) {
        lock.getSessionLock(request).lock();
        try {
            List<Product> currentList = getRecentViewedList(request).getItems();
            if (currentList.size() >= size) {
                if (currentList.contains(product)) {
                    currentList.remove(product);
                } else {
                    currentList.remove(0);
                }
                currentList.add(product);
            } else {
                if (!currentList.contains(product)) {
                    currentList.add(product);
                }
            }
        } finally {
            request.setAttribute("product", product);
            request.setAttribute("cart", DefaultCartService.getInstance().getCart(request));
            request.setAttribute("recentViewedList", getRecentViewedList(request).getItems());
            lock.getSessionLock(request).unlock();
        }
    }

    public RecentViewedList getRecentViewedList(HttpServletRequest request) {
        lock.getSessionLock(request).lock();
        try {
            RecentViewedList recentViewedList = (RecentViewedList) request.getSession().getAttribute(RECENTVIEWED_SESSION_ATTRIBUTE);
            if (recentViewedList == null) {
                request.getSession().setAttribute(RECENTVIEWED_SESSION_ATTRIBUTE, recentViewedList = new RecentViewedList());
            }
            return recentViewedList;
        } finally {
            lock.getSessionLock(request).unlock();
        }
    }
}
