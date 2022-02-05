package com.es.phoneshop.recentViewd;


import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RecentViewedContainer implements RecentViewed {
    private static final String RECENTVIEWED_SESSION_ATTRIBUTE = RecentViewedContainer.class.getName() + ".recentViewed";
    private static RecentViewed instance;
    private static final Object mutex = new Object();
    private final ReadWriteLock rwLock;
    private int size;

    private RecentViewedContainer() {
        rwLock = new ReentrantReadWriteLock();
        size = 3;
    }

    public static RecentViewed getInstance() {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new RecentViewedContainer();
                }
            }
        }
        return instance;
    }

    public void addToRecentViewed(RecentViewedList recentViewedList, Product product) {
        rwLock.writeLock().lock();
        try {
            List<Product> currentList = recentViewedList.getItems();
            if (currentList.size() >= size) {
                if (currentList.contains(product)) {
                    currentList.remove(product);
                } else {
                    currentList.remove(0);
                }
            }
            currentList.add(product);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public RecentViewedList getRecentViewedList(HttpServletRequest request) {
        rwLock.readLock().lock();
        try {
            RecentViewedList recentViewedList = (RecentViewedList) request.getSession().getAttribute(RECENTVIEWED_SESSION_ATTRIBUTE);
            if (recentViewedList == null) {
                request.getSession().setAttribute(RECENTVIEWED_SESSION_ATTRIBUTE, recentViewedList = new RecentViewedList());
            }
            return recentViewedList;
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
