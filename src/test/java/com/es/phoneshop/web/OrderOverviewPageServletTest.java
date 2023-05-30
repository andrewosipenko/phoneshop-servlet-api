package com.es.phoneshop.web;

import com.es.phoneshop.model.Order;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderOverviewPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    private OrderOverviewPageServlet servlet = new OrderOverviewPageServlet();
    private static final String ORDER_ID_FROM_URL = "/1";
    private static final String ORDER_ID = "1";

    @Before
    public void setup() {
        servlet.init();
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        Order order = new Order();
        order.setSecureId(ORDER_ID);
        servlet.orderDao.save(order);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn(ORDER_ID_FROM_URL);

        servlet.doGet(request, response);

        verify(request).setAttribute(anyString(), any());
        verify(requestDispatcher).forward(request, response);
    }

    @After
    public void clear() {
        servlet.orderDao.delete(1L);
    }
}
