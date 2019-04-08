package com.es.phoneshop.model.product.history;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.Product;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class HttpSessionHistoryService implements HistoryService {
    protected static final String HTTP_SESSION_HISTORY_KEY = "httpHistory";
    private static HttpSessionHistoryService INSTANCE;
    private int maxHistorySize;

    private HttpSessionHistoryService() {
    }

    public static HttpSessionHistoryService getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpSessionHistoryService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpSessionHistoryService();
                }
            }
        }
        return INSTANCE;
    }

    private void add(List<Product> historyProducts, Long productId) throws ProductNotFoundException {
        Product product = ArrayListProductDao.getInstance().getProduct(productId);
        int i;
        for (i = 0; i < historyProducts.size(); ++i) {
            if (historyProducts.get(i).getId().equals(productId)) {
                Product temp = historyProducts.get(i);
                historyProducts.set(i, historyProducts.get(0));
                historyProducts.set(0, temp);
                break;
            }
        }
        if (i == historyProducts.size()) {
            historyProducts.add(0, product);
        }
        if (i == maxHistorySize) {
            historyProducts.remove(maxHistorySize - 1);
        }
    }

    @Override
    public void update(HttpSession session, Long productId) throws ProductNotFoundException {
        if (session.getAttribute(HTTP_SESSION_HISTORY_KEY) == null) {
            List<Product> historyProducts = new ArrayList<>();
            session.setAttribute(HTTP_SESSION_HISTORY_KEY, historyProducts);
        }
        @SuppressWarnings("unchecked") List<Product> historyProducts =
                (List<Product>) session.getAttribute(HTTP_SESSION_HISTORY_KEY);
        if (historyProducts != null) {
            if (productId != null) {
                add(historyProducts, productId);
            }
            session.setAttribute(HTTP_SESSION_HISTORY_KEY, historyProducts);
        }
    }

    public void setMaxHistorySize(int maxHistorySize) {
        this.maxHistorySize = maxHistorySize;
    }
}
