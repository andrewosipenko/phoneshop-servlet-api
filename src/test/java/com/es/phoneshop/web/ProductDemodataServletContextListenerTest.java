package com.es.phoneshop.web;

import com.es.phoneshop.core.dao.product.ArrayListProductDao;
import com.es.phoneshop.web.listeners.ProductDemodataServletContextListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContextEvent;

import static org.junit.Assert.assertNotEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProductDemodataServletContextListenerTest {
    private final ProductDemodataServletContextListener productDemodataServletContextListener =
            new ProductDemodataServletContextListener();
    @Mock
    private ServletContextEvent servletContextEvent;

    @Test
    public void testContextInitialized() {
        productDemodataServletContextListener.contextInitialized(servletContextEvent);
        assertNotEquals(ArrayListProductDao.getInstance().findProducts().size(), 0);
    }
}
