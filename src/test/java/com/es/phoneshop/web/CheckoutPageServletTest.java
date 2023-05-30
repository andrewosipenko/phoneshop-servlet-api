package com.es.phoneshop.web;

import com.es.phoneshop.model.Cart;
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
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private Map<String, String> errors;
    private Cart cart;
    private CheckoutPageServlet servlet = new CheckoutPageServlet();
    private Currency usd = Currency.getInstance("USD");

    @Before
    public void setup() {
        servlet.init();
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        Product product = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        servlet.productDao.save(product);
        cart = servlet.cartService.getCart(request);
        servlet.cartService.add(1L, 2, cart);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request, times(2)).setAttribute(anyString(), any());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPost() throws ServletException, IOException {
        servlet.doPost(request, response);

        verify(servlet).handleErrors(request, response, errors, any(), any());
    }

    @After
    public void clear() {
        servlet.cartService.delete(1L, cart);
        servlet.productDao.delete(1L);
    }
}
