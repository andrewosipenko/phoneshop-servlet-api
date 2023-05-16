package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PriceHistoryPageServletTest {
    @Mock
    private ProductDao productDao;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    private PriceHistoryPageServlet servlet = new PriceHistoryPageServlet();
    private static final String PRODUCT_ID_FROM_URL = "/1";
    private static final Long PRODUCT_ID = 1L;

    @Before
    public void setup() {
        servlet.init();
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Product product = new Product();
        product.setId(PRODUCT_ID);
        when(request.getPathInfo()).thenReturn(PRODUCT_ID_FROM_URL);
        when(productDao.getProduct(anyLong())).thenReturn(product);

        servlet.doGet(request, response);

        verify(request).setAttribute(anyString(), anyList());
        verify(requestDispatcher).forward(request, response);
    }
}
