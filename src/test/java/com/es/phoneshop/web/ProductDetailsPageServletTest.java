package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

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
    private HttpSession httpSession;

    @Mock
    private Product product;

    private ProductDetailsPageServlet servlet;

    @Before
    public void setup() throws ServletException {
        servlet = new ProductDetailsPageServlet();
        servlet.init();
        when(request.getLocale()).thenReturn(new Locale("en", "USA"));
        when(request.getPathInfo()).thenReturn("/" + id);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(httpSession);
        when(product.getStock()).thenReturn(100);
        when(product.getId()).thenReturn(id);
        when(product.getPrice()).thenReturn(new BigDecimal(1));

        ArrayListProductDao.getInstance().save(product);
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
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws IOException, ServletException {
        when(request.getParameter("quantity")).thenReturn("1");
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }
}
