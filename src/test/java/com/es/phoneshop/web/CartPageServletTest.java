package com.es.phoneshop.web;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.DefaultCartService;
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
import java.util.Locale;

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
    @Mock
    private DefaultCartService cartService;
    @Mock
    private Cart cart;

    @InjectMocks
    private CartPageServlet servlet = new CartPageServlet();

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getParameterValues("quantity")).thenReturn(new String[]{"1", "2"});
        when(request.getParameterValues("productId")).thenReturn(new String[]{"1", "2"});
        when(request.getLocale()).thenReturn(Locale.ENGLISH);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute("cart", cart);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostWithParseException() throws ServletException, IOException {
        when(request.getParameterValues("quantity")).thenReturn(new String[]{"one", "2"});

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("errorMap"), anyMap());
    }

    @Test
    public void testDoPostWithOutOfStock() throws ServletException, IOException {
        when(request.getParameterValues("quantity")).thenReturn(new String[]{"5", "1"});
        doThrow(new OutOfStockException(5))
                .when(cartService).update(cart, 1L, 5);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("errorMap"), anyMap());
    }
}