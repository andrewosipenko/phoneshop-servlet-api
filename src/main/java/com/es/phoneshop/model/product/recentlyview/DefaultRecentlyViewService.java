package com.es.phoneshop.model.product.recentlyview;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DefaultRecentlyViewService implements RecentlyViewService {

    public static final String RECENTLY_VIEW_SECTION_ATTRIBUTE = DefaultRecentlyViewService.class.getName() + ".recentlyViewSection";
    public static final int SIZE_OF_RECENTLY_VIEW_BAR = 3;
    private static volatile DefaultRecentlyViewService instance;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private DefaultRecentlyViewService() {
    }

    public static DefaultRecentlyViewService getInstance() {
        DefaultRecentlyViewService result = instance;
        if (result != null) {
            return result;
        }
        synchronized (DefaultRecentlyViewService.class) {
            if (instance == null) {
                instance = new DefaultRecentlyViewService();
            }
            return instance;
        }
    }

    @Override
    public synchronized void add(RecentlyViewSection recentlyViewSection, HttpServletRequest request, Product product) {
        List<Product> recentlyViewList = recentlyViewSection.getRecentlyView();
        recentlyViewList.remove(product);
        if (recentlyViewList.size() >= SIZE_OF_RECENTLY_VIEW_BAR) {
            recentlyViewList.remove(SIZE_OF_RECENTLY_VIEW_BAR - 1);
        }
        recentlyViewList.add(0, product);
    }

    @Override
    public RecentlyViewSection getRecentlyViewSection(HttpServletRequest request) {
        lock.readLock().lock();
        HttpSession session = request.getSession();
        RecentlyViewSection recentlyViewSection =
                (RecentlyViewSection) session.getAttribute(RECENTLY_VIEW_SECTION_ATTRIBUTE);
        lock.readLock().unlock();
        if (recentlyViewSection == null) {
            lock.writeLock().lock();
            session.setAttribute(RECENTLY_VIEW_SECTION_ATTRIBUTE, recentlyViewSection = new RecentlyViewSection());
            lock.writeLock().unlock();
        }
        return recentlyViewSection;
    }
}
