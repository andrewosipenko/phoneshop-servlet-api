package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
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
        assertNotEquals(ArrayListProductDao.getInstance().findProducts().size(), 0);
    }
}
