package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.services.RecentlyViewedService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {

    @Mock
    private ProductDao productDao;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private RecentlyViewedService recentlyViewedService;

    @InjectMocks
    private ProductListPageServlet servlet;

    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;


    private final String TEST_SEARCHER = "TEST_SEARCHER";
    private final String TEST_ORDER = "TEST_ORDER";
    private final String TEST_SORTER = "TEST_SORTER";
    private final String PATH = "/WEB-INF/pages/productList.jsp";


    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameter("searcher")).thenReturn(TEST_SEARCHER);
        when(request.getParameter("order")).thenReturn(TEST_ORDER);
        when(request.getParameter("sort")).thenReturn(TEST_SORTER);
        when(request.getRequestDispatcher(PATH)).thenReturn(requestDispatcher);
        when(productDao.findProducts(TEST_SEARCHER, TEST_ORDER, TEST_SORTER)).thenReturn(Arrays.asList(product1, product2, product3));
        when(recentlyViewedService.getViewedProducts(request)).thenReturn(Arrays.asList(product1, product2));
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute("products", productDao.findProducts(TEST_SEARCHER, TEST_ORDER, TEST_SORTER));
        verify(request).setAttribute("viewedProducts", recentlyViewedService.getViewedProducts(request));
        verify(request, times(1)).getRequestDispatcher(PATH);
        verify(requestDispatcher).forward(request, response);
    }
}