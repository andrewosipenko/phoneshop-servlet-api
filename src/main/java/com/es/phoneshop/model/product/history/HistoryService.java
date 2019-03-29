package com.es.phoneshop.model.product.history;

import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

public interface HistoryService {
    void add(History customerHistory, Long productId) throws ProductNotFoundException;
}
