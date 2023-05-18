package com.es.phoneshop.service;

import com.es.phoneshop.model.BrowsingHistory;
import com.es.phoneshop.model.Cart;
import jakarta.servlet.http.HttpServletRequest;

public interface BrowsingHistoryService {
    BrowsingHistory getBrowsingHistory(HttpServletRequest request);

    void add(Long productId, BrowsingHistory browsingHistory);
}
