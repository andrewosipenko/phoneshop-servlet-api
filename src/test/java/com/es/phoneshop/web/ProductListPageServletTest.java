package com.es.phoneshop.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {
    private final ProductListPageServlet servlet = new ProductListPageServlet();
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig config;

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute(eq("products"), any());
    }
    @Test
    public void testDoPostGetProduct() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("findProduct");
        when(request.getParameter("phoneId")).thenReturn("-1");
        servlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute(eq("products"), any());
    }
    @Test
    public void testDoPostDelete() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("deleteProducts");
        when(request.getParameter("phoneIdToDelete")).thenReturn("-1");
        servlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute(eq("products"), any());
    }
    @Test
    public void testDoPostFindNotNullProducts() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("findNotNullProducts");
        servlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute(eq("products"), any());
    }
}