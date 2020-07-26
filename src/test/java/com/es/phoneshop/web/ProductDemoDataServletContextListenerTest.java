package com.es.phoneshop.web;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.model.product.dao.TestableSingletonProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDemoDataServletContextListenerTest {

    @Mock
    ServletContextEvent servletContextEvent;

    @Mock
    ServletContext servletContext;


    private ProductDemoDataServletContextListener listener = new ProductDemoDataServletContextListener();
    private TestableSingletonProductDao<List<Product>> productDao = ArrayListProductDao.getInstance();

    @Before
    public void setup(){
        productDao = ArrayListProductDao.getInstance();
        productDao.set(new LinkedList<>());
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
    }


    @Test
    public void contextInitTestWithFalseParam(){
        when(servletContext.getInitParameter("insertDemoData")).thenReturn("false");
        listener.contextInitialized(servletContextEvent);
        assertTrue(productDao.getAll().isEmpty());
    }

    @Test
    public void contextInitTestWithTrueParam(){
        when(servletContext.getInitParameter("insertDemoData")).thenReturn("true");
        listener.contextInitialized(servletContextEvent);
        assertFalse(productDao.getAll().isEmpty());
    }
}
