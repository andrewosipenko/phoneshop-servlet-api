package com.es.phoneshop.model.recentView;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HttpSessionRecentViewService implements RecentViewService {
    private static final String RECENT_VIEW_SESSION_ATTRIBUTE = HttpSessionRecentViewService.class.getName() + ".recentView";
    private static final String LOCK_SESSION_ATTRIBUTE = HttpSessionRecentViewService.class.getName() + ".lock";
    private static HttpSessionRecentViewService instance;

    public static synchronized HttpSessionRecentViewService getInstance() {
        if (instance == null) {
            instance = new HttpSessionRecentViewService();
        }
        return instance;
    }

    private HttpSessionRecentViewService() {}

    @Override
    public RecentView getRecentView(HttpServletRequest request) {
        Lock lock = (Lock) request.getSession().getAttribute(LOCK_SESSION_ATTRIBUTE);
        if(lock == null) {
            synchronized (request.getSession()) {
                lock = (Lock) request.getSession().getAttribute(LOCK_SESSION_ATTRIBUTE);
                if (lock == null) {
                    lock = new ReentrantLock();
                    request.getSession().setAttribute(LOCK_SESSION_ATTRIBUTE, lock);
                }
            }
        }
        lock.lock();
        try {
            RecentView recentView = (RecentView) request.getSession().getAttribute(RECENT_VIEW_SESSION_ATTRIBUTE);
            if (recentView == null) {
                recentView = new RecentView(lock);
                request.getSession().setAttribute(RECENT_VIEW_SESSION_ATTRIBUTE, recentView);
            }
            return recentView;
        } finally {
            lock.unlock();
        }
    }
}
