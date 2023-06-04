package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.impl.ProductServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PriceHistoryPageServletTest {

    @Mock
    private ProductServiceImpl productService;

    @Mock
    private ServletConfig servletConfig;

    @Mock
    private ServletContext servletContext;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    private PriceHistoryPageServlet servlet;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        servlet = new PriceHistoryPageServlet();
        servlet.init(servletConfig);
        servlet.setProductService(productService);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void givenValidProductId_whenDoGet_thenValidAttributeAndForward() throws Exception {
        long productId = 1L;
        Product product = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), null, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        when(productService.getProduct(productId)).thenReturn(product);
        when(request.getPathInfo()).thenReturn("/" + productId);

        servlet.doGet(request, response);

        verify(request).setAttribute("product", product);
        verify(requestDispatcher).forward(request, response);
    }
}