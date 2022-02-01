package com.es.phoneshop.web.listener;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DemoDataServletContextListenerTest {
    @Mock
    private ServletContextEvent servletContextEvent;
    @Mock
    private ServletContext servletContext;

    private final DemoDataServletContextListener servletContextListener = new DemoDataServletContextListener();

    private final ProductDao productDao = ArrayListProductDao.getInstance();

    @Before
    public void setup() {
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter(eq("insertDemoData"))).thenReturn("true");
    }

    @Test
    public void testContextInitialized() {
        servletContextListener.contextInitialized(servletContextEvent);

        assertEquals(12, productDao.findProducts(null, null, null).size());
    }
}
