package com.es.phoneshop.web;

import com.es.phoneshop.core.dao.order.DeliveryMode;
import com.es.phoneshop.core.dao.order.Order;
import com.es.phoneshop.core.dao.order.OrderDao;
import com.es.phoneshop.core.order.OrderService;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {

    private final CheckoutPageServlet checkoutPageServlet = new CheckoutPageServlet();
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private OrderDao orderDao;
    @Mock
    private OrderService orderService;
    @Mock
    private Order order;

    @Before
    public void start() {
        when(orderService.getDeliveryMode(request)).thenReturn(DeliveryMode.STORE_PICKUP);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(orderService.createOrder(request)).thenReturn(order);
        when(order.getDeliveryMode()).thenReturn(DeliveryMode.STORE_PICKUP);
        checkoutPageServlet.setOrderService(orderService);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        checkoutPageServlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        checkoutPageServlet.setOrderDao(orderDao);
        checkoutPageServlet.doPost(request, response);
        verify(request).getRequestDispatcher(anyString());
        verify(request.getRequestDispatcher(anyString())).forward(request, response);
    }
}
