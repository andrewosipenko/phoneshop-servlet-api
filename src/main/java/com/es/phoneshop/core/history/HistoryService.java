package com.es.phoneshop.core.history;

import com.es.phoneshop.core.exceptions.ProductNotFoundException;

import javax.servlet.http.HttpSession;

public interface HistoryService {
    void update(HttpSession session, Long productId) throws ProductNotFoundException;
}
