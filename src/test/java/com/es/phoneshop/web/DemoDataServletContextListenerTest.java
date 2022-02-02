package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)

public class DemoDataServletContextListenerTest {

    @Mock
    private ServletContextEvent servletContextEvent;

    @Mock
    private ServletContext servletContext;

    private final DemoDataServletContextListener servlet = new DemoDataServletContextListener();

    @Test
    public void testContextInitializedTrue() {
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter("insertDemoData")).thenReturn("true");
        servlet.contextInitialized(servletContextEvent);
        ProductDao pd = ArrayListProductDao.getInstance();
        assertFalse(pd.findProducts(null, null, null).isEmpty());
    }

    @Test
    public void testContextInitializedFalse() {
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter("insertDemoData")).thenReturn("false");
        servlet.contextInitialized(servletContextEvent);
        ProductDao pd = ArrayListProductDao.getInstance();
        assertTrue(pd.findProducts(null, null, null).isEmpty());
    }
}
