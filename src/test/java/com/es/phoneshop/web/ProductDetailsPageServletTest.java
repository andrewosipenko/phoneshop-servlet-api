package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.product.Product;
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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;
    @Mock
    private Product product1;

    @InjectMocks
    private final ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() {
        when(productDao.getProduct(1L)).thenReturn(product1);

    }

    @Test
    public void testProductPriceHistoryDoGet() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/priceHistory/1");
        when(request.getRequestDispatcher("/WEB-INF/pages/productPriceHistory.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute("product", productDao.getProduct(1L));
        verify(requestDispatcher).forward(request, response);

    }

    @Test
    public void testProductDetailsDoGet() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute("product", productDao.getProduct(1L));
        verify(requestDispatcher).forward(request, response);

    }

    @Test(expected = RuntimeException.class)
    public void testErrorProductNotFoundDoGet() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/100");
        when(request.getRequestDispatcher("/WEB-INF/pages/errorProductNotFound.jsp")).thenReturn(requestDispatcher);
        when(productDao.getProduct(100L)).thenThrow(new RuntimeException());

        servlet.doGet(request, response);

        try {
            verify(request).setAttribute("product", productDao.getProduct(100L));
        }
        catch (RuntimeException ex) {
            verify(request).setAttribute("id", "100");
            verify(requestDispatcher).forward(request, response);
        }
    }

}