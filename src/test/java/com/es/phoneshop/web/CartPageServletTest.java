package com.es.phoneshop.web;

import com.es.phoneshop.model.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    private CartPageServlet servlet = new CartPageServlet();
    private Locale locale = new Locale("RUS");
    private static final String[] PRODUCT_IDS = {"1"};
    private static final String[] QUANTITIES = {"1"};
    private static final String[] QUANTITY_MORE_THAN_STOCK = {"111"};
    private static final String[] INVALID_QUANTITY = {"aaa"};
    private static final String PRODUCT_ID = "productId";
    private static final String QUANTITY = "quantity";
    private static final String ERROR = "error";


    @Before
    public void setup() {
        servlet.init();
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameterValues(PRODUCT_ID)).thenReturn(PRODUCT_IDS);
        when(request.getParameterValues(QUANTITY)).thenReturn(QUANTITIES);
        when(request.getLocale()).thenReturn(locale);
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        servlet.productDao.save(product);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute(anyString(), anyList());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPost() throws ServletException, IOException {
        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testForProductQuantityMoreThanStock() throws ServletException, IOException {
        when(request.getParameterValues(QUANTITY)).thenReturn(QUANTITY_MORE_THAN_STOCK);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq(ERROR), anyString());
    }

    @Test
    public void testInvalidProductQuantity() throws ServletException, IOException {
        when(request.getParameterValues(QUANTITY)).thenReturn(INVALID_QUANTITY);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq(ERROR), anyString());
    }

    @After
    public void clear() {
        servlet.productDao.delete(1L);
    }
}
