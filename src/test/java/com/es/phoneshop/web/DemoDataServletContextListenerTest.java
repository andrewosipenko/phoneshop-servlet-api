package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DemoDataServletContextListenerTest extends TestCase {
    @Mock
    private ServletContextEvent event;
    @Mock
    private ServletContext servletContext;
    @Mock
    private ArrayListProductDao productDao;
    @InjectMocks
    private DemoDataServletContextListener listener = new DemoDataServletContextListener();

    @Before
    public void setup() {
        when(event.getServletContext()).thenReturn(servletContext);
    }

    @Test
    public void testContextInitializedInsertDemoData() {
        when(servletContext.getInitParameter("insertDemoData")).thenReturn(String.valueOf(true));

        listener.contextInitialized(event);

        verify(productDao, atLeast(1)).save(any());
    }

    @Test
    public void testContextInitializedDoNotInsertDemoData() {
        when(servletContext.getInitParameter("insertDemoData")).thenReturn(String.valueOf(false));

        listener.contextInitialized(event);

        verify(productDao, times(0)).save(any());
    }
}