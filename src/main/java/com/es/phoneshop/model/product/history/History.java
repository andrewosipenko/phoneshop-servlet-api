package com.es.phoneshop.model.product.history;

import com.es.phoneshop.model.product.dao.Product;

import java.util.ArrayList;
import java.util.List;

public class History {
    final static int MAX_HISTORY_SIZE = 20;
    private List<Product> historyProducts;

    History() {
        historyProducts = new ArrayList<>();
    }

    public List<Product> getHistoryProducts() {
        return historyProducts;
    }
}
