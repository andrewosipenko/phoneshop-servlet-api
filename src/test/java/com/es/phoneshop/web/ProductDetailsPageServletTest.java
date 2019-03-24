package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private static ServletContextEvent servletContextEvent;

    private final ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();
    private static final ProductDemodataServletContextListener productDemodataServletContextListener
            = new ProductDemodataServletContextListener();

    @BeforeClass
    public static void start() {
        productDemodataServletContextListener.contextInitialized(servletContextEvent);
    }

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Long CORRECT_ID = 1L;
        when(request.getPathInfo()).thenReturn("/" + CORRECT_ID);
        servlet.doGet(request, response);
        verify(request).setAttribute(ProductDetailsPageServlet.ID, CORRECT_ID);
        verify(request).setAttribute(ProductDetailsPageServlet.PRODUCT, ArrayListProductDao.getInstance().getProduct(CORRECT_ID));
        String PATH = "/WEB-INF/pages/productDetails.jsp";
        verify(request).getRequestDispatcher(PATH);
        verify(requestDispatcher).forward(request, response);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testInvalidIdPath() throws ServletException, IOException {
        Long INVALID_ID = Long.MAX_VALUE;
        when((request.getPathInfo())).thenReturn("/" + INVALID_ID);
        servlet.doGet(request, response);
        verify(request).setAttribute(ProductDetailsPageServlet.ID, INVALID_ID);
    }
}
