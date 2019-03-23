package com.es.phoneshop.web;

import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.ServletContextEvent;

import static org.junit.Assert.assertNotEquals;

public class ProductDemodataServletContextListenerTest {
    @Mock
    ServletContextEvent servletContextEvent;

    private final ProductDemodataServletContextListener productDemodataServletContextListener =
            new ProductDemodataServletContextListener();

    @Test
    public void testContextInitialized() {
        productDemodataServletContextListener.contextInitialized(servletContextEvent);
        assertNotEquals(productDemodataServletContextListener.productDao.findProducts().size(), 0);
    }
}
