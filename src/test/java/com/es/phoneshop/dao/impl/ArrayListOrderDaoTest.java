package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListOrderDaoTest {
    @Mock
    private OrderDao orderDao;
    private static final Long ORDER_ID = 1L;
    private static final String SECURED_ORDER_ID = "g34grt6";
    private static final String NOT_EXISTING_SECURED_ORDER_ID = "ggggggggg";

    @Test
    public void testGetOrder() {
        Order order = new Order();
        order.setId(ORDER_ID);
        when(orderDao.getOrder(anyLong())).thenReturn(order);

        Order result = orderDao.getOrder(ORDER_ID);

        assertEquals(ORDER_ID, result.getId());
    }

    @Test
    public void testGetOrderBySecureId() {
        Order order = new Order();
        order.setSecureId(SECURED_ORDER_ID);
        when(orderDao.getOrderBySecureId(anyString())).thenReturn(order);

        Order result = orderDao.getOrderBySecureId(SECURED_ORDER_ID);

        assertEquals(SECURED_ORDER_ID, result.getSecureId());
    }

    @Test
    public void testSave() {
        Order order = new Order();

        orderDao.save(order);

        verify(orderDao).save(order);
    }

    @Test(expected = OrderNotFoundException.class)
    public void testExceptionForNotFoundOrderById() {
        when(orderDao.getOrderBySecureId(anyString())).thenThrow(new OrderNotFoundException());

        orderDao.getOrderBySecureId(NOT_EXISTING_SECURED_ORDER_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionForNullOrderParameter() {
        doThrow(new IllegalArgumentException()).when(orderDao).save(null);

        orderDao.save(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionForNullIdParameter() {
        doThrow(new IllegalArgumentException()).when(orderDao).getOrderBySecureId(null);

        orderDao.getOrderBySecureId(null);
    }
}