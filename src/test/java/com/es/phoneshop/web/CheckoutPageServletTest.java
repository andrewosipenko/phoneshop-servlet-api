package com.es.phoneshop.web;

import com.es.phoneshop.model.product.dao.order.Order;
import com.es.phoneshop.model.product.dao.order.OrderDao;
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
    private Order order;
    @Mock
    private OrderDao orderDao;

    @Before
    public void start() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        checkoutPageServlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        checkoutPageServlet.setOrderDao(orderDao);
        when(orderDao.getOrder(request)).thenReturn(order);
        when(orderDao.isOrderValid(order)).thenReturn(false);
        checkoutPageServlet.doPost(request, response);
        verify(request).getRequestDispatcher(anyString());
        verify(request.getRequestDispatcher(anyString())).forward(request, response);
    }
}
