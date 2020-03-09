package com.es.phoneshop.web;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.enums.SortField;
import com.es.phoneshop.model.enums.SortOrder;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.impl.DefaultRecentlyViewedService;
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

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ArrayListProductDao productDao;
    @Mock
    private DefaultRecentlyViewedService recentlyViewedService;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @InjectMocks
    private ProductListPageServlet servlet = new ProductListPageServlet();

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(productDao.findProducts(null, null, null)).thenReturn(Arrays.asList(product1, product2));
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(productDao).findProducts(null, null, null);
        verify(request).setAttribute("recentlyViewed", recentlyViewedService.getProducts(request));
        verify(request).setAttribute("products", productDao.findProducts(null, null, null));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetWithSort() throws ServletException, IOException {
        when(request.getParameter("sortField")).thenReturn("price");
        when(request.getParameter("sortOrder")).thenReturn("asc");

        servlet.doGet(request, response);

        verify(productDao).findProducts(null, SortField.PRICE, SortOrder.ASC);
        verify(request).setAttribute("products",
                productDao.findProducts(null, SortField.PRICE, SortOrder.ASC));
        verify(requestDispatcher).forward(request, response);
    }
}