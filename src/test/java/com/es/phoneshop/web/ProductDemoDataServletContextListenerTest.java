package com.es.phoneshop.web;

import jakarta.servlet.ServletContextEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProductDemoDataServletContextListenerTest {
    @Mock
    private ProductDemoDataServletContextListener servletContextListener;
    @Mock
    private ServletContextEvent servletContextEvent;

    @Test
    public void testContextInitialized() {
        servletContextListener.contextInitialized(servletContextEvent);

        verify(servletContextListener).contextInitialized(any());
    }

    @Test
    public void testContextDestroyed() {
        servletContextListener.contextDestroyed(servletContextEvent);

        verify(servletContextListener).contextDestroyed(any());
    }
}
