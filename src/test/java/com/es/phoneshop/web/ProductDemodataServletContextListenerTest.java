package com.es.phoneshop.web;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.ServletContextEvent;

import static org.junit.Assert.assertNotEquals;

public class ProductDemodataServletContextListenerTest {
    private final ProductDemodataServletContextListener productDemodataServletContextListener =
            new ProductDemodataServletContextListener();
    @Mock
    ServletContextEvent servletContextEvent;

    @Test
    public void testContextInitialized() {
        productDemodataServletContextListener.contextInitialized(servletContextEvent);
        assertNotEquals(ArrayListProductDao.getInstance().findProducts().size(), 0);
    }
}
