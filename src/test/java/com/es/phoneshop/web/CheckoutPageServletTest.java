package com.es.phoneshop.web;

import com.es.phoneshop.order.Order;
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
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    protected ServletConfig config;
    @Mock
    protected HttpSession httpSession;

    private final CheckoutPageServlet servlet = new CheckoutPageServlet();


    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("firstName")).thenReturn("Ivan");
        when(request.getParameter("lastName")).thenReturn("Ivanov");
        when(request.getParameter("phone")).thenReturn("333");
        when(request.getParameter("deliveryDate")).thenReturn("12/12/2021").thenReturn("wrong");
        when(request.getParameter("deliveryAddress")).thenReturn("defaultAddress");
        when(request.getParameter("paymentMethod")).thenReturn("CARD");
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("order"), any(Order.class));
        verify(request).setAttribute(eq("paymentMethods"), anyCollection());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.doPost(request, response);
        verify(response).sendRedirect(request.getContextPath() + "/order/overview/" + anyString());
        servlet.doPost(request, response);
        verify(request).setAttribute(eq("errors"), anyMap());
        verify(request).setAttribute(eq("order"), any(Order.class));
        verify(request).setAttribute(eq("paymentMethods"), anyCollection());
        verify(requestDispatcher).forward(request, response);
    }
}