package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import javax.servlet.ServletContextEvent;
import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ProductDemoDataServletContextListenerTest {

    private ProductDemoDataServletContextListener listener;

    @Mock
    private ServletContextEvent servletContextEvent;

    @Before
    public void setup() {
        listener = new ProductDemoDataServletContextListener();
    }

    @Test
    public void testCallMethods() {
        ProductDao productDao = ArrayListProductDao.getInstance();
        listener.contextInitialized(servletContextEvent);
        listener.contextDestroyed(servletContextEvent);
        assertTrue(!productDao.findProducts().isEmpty());
    }
}