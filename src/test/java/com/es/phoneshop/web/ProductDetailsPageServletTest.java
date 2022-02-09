package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    protected ServletConfig config;
    @Mock
    private Product product;

    private final ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        Currency usd = Currency.getInstance("USD");
        product = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        product.setId((long) 13);

    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
//        verify(requestDispatcher).forward(request, response);
//        verify(request).setAttribute(eq("product"), product);
    }

}