package com.es.phoneshop.web;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.es.phoneshop.service.ProductServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class ProductDetailsPageServletTest {

    private ProductDetailsPageServlet servlet;

    @Mock
    private ProductServiceImpl productService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private ServletConfig servletConfig;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        servlet = new ProductDetailsPageServlet();
        servlet.init(servletConfig);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void givenValidProductId_whenDoGet_thenValidAttributeAndForward() throws Exception {
        ProductServiceImpl productService1 = ProductServiceImpl.getInstance();
        Product product = new Product();
        String productId = String.valueOf(product.getId());
        productService1.save(product);

        when(request.getPathInfo()).thenReturn("/" + productId);

        servlet.doGet(request, response);

        verify(request).setAttribute("product", product);
        verify(requestDispatcher).forward(request, response);
    }

    @Test(expected = ProductNotFoundException.class)
    public void givenInvalidProductId_whenDoGet_thenThrowProductNotFoundException() throws Exception {
        String productId = "12345";
        when(request.getPathInfo()).thenReturn("/" + productId);
        when(productService.getProduct(Long.parseLong(productId))).thenReturn(Optional.empty());

        servlet.doGet(request, response);
    }
}