package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {
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
    @Mock
    private Product product2;

    @InjectMocks
    private final ProductListPageServlet servlet = new ProductListPageServlet();

    @Before
    public void setup() {
        List<Product> testProducts = new ArrayList<>();
        testProducts.add(product1);
        testProducts.add(product2);
        when(productDao.findProducts()).thenReturn(testProducts);
        when(request.getRequestDispatcher("/WEB-INF/pages/productList.jsp")).thenReturn(requestDispatcher);

    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute("products", productDao.findProducts());
        verify(requestDispatcher).forward(request, response);

    }

}