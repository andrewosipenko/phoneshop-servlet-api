package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.ProductService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

public class DemoDataServletContextListenerTest {

    @Mock
    private ProductService productService;

    private DemoDataServletContextListener listener;

    @Before
    public void setUp() {
        listener = new DemoDataServletContextListener();
        productService = mock(ProductService.class);
        listener.setProductService(productService);
    }

    @Test
    public void givenTrueInsertData_whenContextInitialized_thenValidSaveProduct() {
        ServletContextEvent event = mock(ServletContextEvent.class);
        ServletContext servletContext = mock(ServletContext.class);
        when(event.getServletContext()).thenReturn(servletContext);
        when(event.getServletContext().getInitParameter("insertDemoData")).thenReturn("true");

        listener.contextInitialized(event);

        verify(productService, times(12)).save(any(Product.class));
    }

    @Test
    public void givenFalseInsertData_whenContextInitialized_thenNotSaveProduct() {
        ServletContextEvent event = mock(ServletContextEvent.class);
        ServletContext servletContext = mock(ServletContext.class);
        when(event.getServletContext()).thenReturn(servletContext);
        when(event.getServletContext().getInitParameter("insertDemoData")).thenReturn("false");

        listener.contextInitialized(event);

        verify(productService, never()).save(any(Product.class));
    }
}