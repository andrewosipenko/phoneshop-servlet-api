package com.es.phoneshop.web.listeners;

import com.es.phoneshop.core.history.HttpSessionHistoryService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ProductRecentlyViewedServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String maxHistorySize = servletContextEvent.getServletContext().getInitParameter("maxHistorySize");
        HttpSessionHistoryService.getInstance().setMaxHistorySize(Integer.valueOf(maxHistorySize));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
