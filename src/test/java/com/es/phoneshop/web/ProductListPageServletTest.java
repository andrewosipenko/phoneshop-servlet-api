package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    private List<Product> products = new ArrayList<>();

    private ProductListPageServlet servlet = new ProductListPageServlet();

    private final String path = "/WEB-INF/pages/productList.jsp";

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getRequestDispatcher(path)).thenReturn(requestDispatcher);
        servlet.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher(path);
        verify(request).setAttribute("products", products);
        verify(requestDispatcher).forward(request, response);
    }
}