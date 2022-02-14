package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDemodataServletContextListenerTest {
    @Mock
    protected ServletContextEvent event;
    @Mock
    protected ServletContext servletContext;

    private final ProductDemoDataServletContextListener listener = new ProductDemoDataServletContextListener();
    private ProductDao productDao;

    @Before
    public void setup() {
        when(event.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter("insertDemoData")).thenReturn("true");
        productDao = ArrayListProductDao.getInstance();
    }

    @Test
    public void test() {
        listener.contextInitialized(event);
        assertFalse(productDao.findProducts("", null).isEmpty());
    }

}