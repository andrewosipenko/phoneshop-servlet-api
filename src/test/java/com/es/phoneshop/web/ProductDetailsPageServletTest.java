package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
    public class ProductDetailsPageServletTest {

    private long id = 1;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private HttpSession httpSession;

    @Mock
    private Product product;

    private ProductDetailsPageServlet servlet;

    @Before
    public void setup() {
        servlet = new ProductDetailsPageServlet();
        servlet.init();
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn("/" + id);
        when(product.getId()).thenReturn(id);

        try {
            ArrayListProductDao.getInstance().save(product);
        } catch (IllegalArgumentException e) {
        }
    }

    @After
    public void destroy() {
        try {
            ArrayListProductDao.getInstance().delete(id);
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }
}

