package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.HttpSessionViewedProductService;
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
import java.io.IOException;
import java.util.Deque;

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
    private HttpSessionViewedProductService viewedProducts;

    @Mock
    private Deque<Product> dequeViewedProducts;

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
        when(viewedProducts.getViewedProducts(request.getSession())).thenReturn(dequeViewedProducts);
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
        verify(request).setAttribute("viewedProducts", dequeViewedProducts);
        verify(request).setAttribute("product", product);
        verify(requestDispatcher).forward(request, response);

    }

    @Test
    public void testDoPost() throws IOException, ServletException {
        when(request.getParameter("quantity")).thenReturn("1");
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostWithQuantityNotNumber() throws IOException, ServletException {
        when(request.getParameter("quantity")).thenReturn("symbol");
        servlet.doPost(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostWithNegativeQuantity() throws IOException, ServletException {
        when(request.getParameter("quantity")).thenReturn("-1");
        servlet.doPost(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostWithStockLessQuantity() throws IOException, ServletException {
        when(request.getParameter("quantity")).thenReturn("101");
        servlet.doPost(request, response);
        verify(requestDispatcher).forward(request, response);
    }
}

