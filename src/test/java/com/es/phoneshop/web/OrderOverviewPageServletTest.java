package com.es.phoneshop.web;

import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.dao.order.Order;
import com.es.phoneshop.model.product.dao.order.OrderDao;
import com.es.phoneshop.model.product.exceptions.OrderNotFoundException;
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
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class OrderOverviewPageServletTest {

    private final OrderOverviewPageServlet orderOverviewPageServlet = new OrderOverviewPageServlet();
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
    @Mock
    private CartService cartService;

    @Before
    public void start() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGetGood() throws ServletException, IOException {
        UUID uuid = UUID.randomUUID();
        orderOverviewPageServlet.setOrderDao(orderDao);
        orderOverviewPageServlet.setCartService(cartService);
        when(request.getPathInfo()).thenReturn("/" + uuid);
        Optional<Order> optionalOrder = Optional.of(order);
        when(orderDao.findById(uuid)).thenReturn(optionalOrder);
        orderOverviewPageServlet.doGet(request, response);
        verify(request).setAttribute(orderOverviewPageServlet.ORDER, optionalOrder.get());
        verify(cartService).clearCart(request);
        verify(requestDispatcher).forward(request, response);
    }

    @Test(expected = OrderNotFoundException.class)
    public void testDoGetBad() throws ServletException, IOException {
        UUID uuid = UUID.randomUUID();
        orderOverviewPageServlet.setOrderDao(orderDao);
        orderOverviewPageServlet.setCartService(cartService);
        when(request.getPathInfo()).thenReturn("/" + uuid);
        Optional<Order> optionalOrder = Optional.empty();
        when(orderDao.findById(uuid)).thenReturn(optionalOrder);
        orderOverviewPageServlet.doGet(request, response);
    }
}
