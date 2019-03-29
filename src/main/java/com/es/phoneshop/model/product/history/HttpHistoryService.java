package com.es.phoneshop.model.product.history;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.Product;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import java.util.List;

public class HttpHistoryService implements HistoryService {
    private static HttpHistoryService INSTANCE;

    @Override
    public void add(History customerHistory, Long productId) throws ProductNotFoundException {
        Product product = ArrayListProductDao.getInstance().getProduct(productId);
        List<Product> historyProducts = customerHistory.getHistoryProducts();
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
        if (i == History.MAX_HISTORY_SIZE) {
            historyProducts.remove(History.MAX_HISTORY_SIZE - 1);
        }
    }

    public static HttpHistoryService getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpHistoryService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpHistoryService();
                }
            }
        }
        return INSTANCE;
    }
}
