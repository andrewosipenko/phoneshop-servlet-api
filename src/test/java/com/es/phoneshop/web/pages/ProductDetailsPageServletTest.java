package com.es.phoneshop.web.pages;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
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
    private ServletConfig config;

    @Mock
    private HttpSession session;

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getParameter("quantity")).thenReturn("1");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getLocale()).thenReturn(Locale.getDefault());
        when(request.getSession()).thenReturn(session);
        ProductDao productDao = ArrayListProductDao.getInstance();
        Currency currency = java.util.Currency.getInstance("USD");
        Product product = new Product(1L, "test", "HTC EVO Shift 4G", new BigDecimal(320), currency, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        productDao.save(product);

    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher(eq("/WEB-INF/pages/productDetails.jsp"));
        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute(eq("product"), any());
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostQuantityNotANumber() throws ServletException, IOException {
        when(request.getParameter("quantity")).thenReturn("number");
        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Not a number");
        verify(response, never()).sendRedirect(anyString());
    }

    @Test
    public void testDoPostQuantityNegativeANumber() throws ServletException, IOException {
        when(request.getParameter("quantity")).thenReturn("-1");
        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Negative number or zero");
        verify(response, never()).sendRedirect(anyString());
    }
}