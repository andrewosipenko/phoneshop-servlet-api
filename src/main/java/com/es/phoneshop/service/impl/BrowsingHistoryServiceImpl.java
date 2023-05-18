package com.es.phoneshop.service.impl;

import com.es.phoneshop.FunctionalReadWriteLock;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.BrowsingHistory;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.BrowsingHistoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.util.WebUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BrowsingHistoryServiceImpl implements BrowsingHistoryService {
    private ProductDao productDao;
    private final FunctionalReadWriteLock lock;
    private static final String BROWSING_HISTORY_SESSION_ATTRIBUTE
            = BrowsingHistoryServiceImpl.class.getName() + ".browsingHistory";
    private static final int MAX_SIZE_OF_BROWSING_HISTORY = 3;

    private BrowsingHistoryServiceImpl() {
        this.productDao = ArrayListProductDao.getInstance();
        this.lock = new FunctionalReadWriteLock();
    }

    public static BrowsingHistoryServiceImpl getInstance() {
        return BrowsingHistoryServiceImpl.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final BrowsingHistoryServiceImpl INSTANCE = new BrowsingHistoryServiceImpl();
    }

    @Override
    public BrowsingHistory getBrowsingHistory(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object mutex = WebUtils.getSessionMutex(session);
            synchronized (mutex) {
                BrowsingHistory history = (BrowsingHistory) session.getAttribute(BROWSING_HISTORY_SESSION_ATTRIBUTE);
                if (Optional.ofNullable(history).isEmpty()) {
                    session.setAttribute(BROWSING_HISTORY_SESSION_ATTRIBUTE, history = new BrowsingHistory());
                }
                return history;
            }
        }
        return new BrowsingHistory();
    }

    @Override
    public synchronized void add(Long productId, BrowsingHistory browsingHistory) {
        Product product = productDao.getProduct(productId);
        LinkedList<Product> products = browsingHistory.getProducts();
        if (products.contains(product)) {
            products.remove(product);
        }
        products.addFirst(product);
        if (products.size() > MAX_SIZE_OF_BROWSING_HISTORY) {
            products.removeLast();
        }
    }
}
