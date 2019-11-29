package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {
    @Mock
    HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private CartService cartService;
    @Mock
    private OrderService orderService;

    private CheckoutPageServlet servlet = new CheckoutPageServlet();
    private Cart cart;
    private Order order;

    @Before
    public void setUp() {
        servlet.setCartService(cartService);
        servlet.setOrderService(orderService);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        cart = new Cart();
        when(cartService.getCart(request)).thenReturn(cart);
        order = new Order();
        when(orderService.getOrder(cart)).thenReturn(order);
    }

    @Test
    public void testDoGetSuccessfully() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute("order", order);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostSuccessfully() throws ServletException, IOException {
        when(request.getParameter("firstName")).thenReturn("A");
        when(request.getParameter("lastName")).thenReturn("B");
        when(request.getParameter("phone")).thenReturn("2222222222");
        when(request.getParameter("deliveryAddress")).thenReturn("M");
        when(request.getParameter("deliveryDate")).thenReturn("10.10.2020");
        when((request.getParameter("paymentMethod"))).thenReturn("money");

        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostWithEmptyFields() throws ServletException, IOException {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("firstName", "firstName is required");
        when(request.getParameter("firstName")).thenReturn("");
        when(request.getParameter("lastName")).thenReturn("B");
        when(request.getParameter("phone")).thenReturn("2222222222");
        when(request.getParameter("deliveryAddress")).thenReturn("M");
        when(request.getParameter("deliveryDate")).thenReturn("10.10.2020");
        when((request.getParameter("paymentMethod"))).thenReturn("money");

        servlet.doPost(request, response);

        verify(request).setAttribute("errorMap", errorMap);
        verify(request).setAttribute("order", order);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostWithInvalidDate() throws ServletException, IOException {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("deliveryDate", "invalid delivery date");
        when(request.getParameter("firstName")).thenReturn("A");
        when(request.getParameter("lastName")).thenReturn("B");
        when(request.getParameter("phone")).thenReturn("2222222222");
        when(request.getParameter("deliveryAddress")).thenReturn("M");
        when(request.getParameter("deliveryDate")).thenReturn("10");
        when((request.getParameter("paymentMethod"))).thenReturn("money");

        servlet.doPost(request, response);

        verify(request).setAttribute("errorMap", errorMap);
        verify(request).setAttribute("order", order);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostWithInvalidPhone() throws ServletException, IOException {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("phone", "invalid phone number");
        when(request.getParameter("firstName")).thenReturn("A");
        when(request.getParameter("lastName")).thenReturn("B");
        when(request.getParameter("phone")).thenReturn("+usgfrengl");
        when(request.getParameter("deliveryAddress")).thenReturn("M");
        when(request.getParameter("deliveryDate")).thenReturn("10.10.2020");
        when((request.getParameter("paymentMethod"))).thenReturn("money");

        servlet.doPost(request, response);

        verify(request).setAttribute("errorMap", errorMap);
        verify(request).setAttribute("order", order);
        verify(requestDispatcher).forward(request, response);
    }
}
