package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteCartItemServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private Product product;

    DeleteCartItemServlet servlet;

    @Before
    public void setup() throws ServletException {
        servlet = new DeleteCartItemServlet();
        servlet.init();
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getPathInfo()).thenReturn("/1");
        when(product.getId()).thenReturn((long) 1);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute(anyString(), any());
        verify(request.getRequestDispatcher(anyString())).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        ArrayListProductDao.getInstance().save(product);
        servlet.doPost(request, response);
        verify(request).setAttribute(anyString(), any());
        verify(request.getRequestDispatcher(anyString())).forward(request, response);
        ArrayListProductDao.getInstance().delete(product.getId());
    }
}