package com.es.phoneshop.listener;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.ArrayList;

public class RecentlyViewedProductsListener implements HttpSessionListener {

    private static final String RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE = "recentlyViewedProducts";

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setAttribute(RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE, new ArrayList<>());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSessionListener.super.sessionDestroyed(event);
    }
}
