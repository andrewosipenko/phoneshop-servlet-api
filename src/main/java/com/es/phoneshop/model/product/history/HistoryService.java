package com.es.phoneshop.model.product.history;

import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpSession;

public interface HistoryService {
    void update(HttpSession session, Long productId) throws ProductNotFoundException;
}
