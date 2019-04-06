package com.es.phoneshop.web;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDemodataServletContextListenerTest {
    private final ProductDemodataServletContextListener productDemodataServletContextListener =
            new ProductDemodataServletContextListener();
    @Mock
    private ServletContextEvent servletContextEvent;
    @Mock
    private ServletContext servletContext;

    @Before
    public void start() {
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter(anyString())).thenReturn("3");
    }

    @Test
    public void testContextInitialized() {
        productDemodataServletContextListener.contextInitialized(servletContextEvent);
        assertNotEquals(ArrayListProductDao.getInstance().findProducts().size(), 0);
    }
}
