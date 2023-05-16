package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.ProductService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductDetailsPageServletTest {

    @Mock
    private ProductService productService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletContext context;

    @Mock
    private ServletConfig config;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    private ProductDetailsPageServlet servlet;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        servlet = new ProductDetailsPageServlet();
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.init(config);
        when(request.getServletContext()).thenReturn(context);
        when(request.getSession(anyBoolean())).thenReturn(session);
    }

    @Test
    public void givenValidProductId_whenDoGet_thenValidAttributeAndForward() throws Exception {
        long productId = 1L;
        Product product = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), null, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        when(productService.getProduct(productId)).thenReturn(product);
        when(request.getPathInfo()).thenReturn("/" + productId);
        when(request.getSession()).thenReturn(session);
        when(config.getServletContext()).thenReturn(context);

        servlet.setProductService(productService);
        servlet.doGet(request, response);

        verify(request).setAttribute("product", product);
        verify(requestDispatcher).forward(request, response);
    }

    @Test(expected = ProductNotFoundException.class)
    public void givenInvalidProductId_whenDoGet_thenThrowProductNotFoundException() throws Exception {
        String productId = "12345";
        when(request.getPathInfo()).thenReturn("/" + productId);
        when(productService.getProduct(Long.parseLong(productId))).thenReturn(null);

        servlet.doGet(request, response);
    }
}
