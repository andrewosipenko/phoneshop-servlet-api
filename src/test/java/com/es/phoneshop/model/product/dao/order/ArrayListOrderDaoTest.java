package com.es.phoneshop.model.product.dao.order;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListOrderDaoTest {
    private static OrderDao orderDao;
    @Mock
    Order order;

    @BeforeClass
    public static void start() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Test
    public void testFindById() {
        UUID uuid = UUID.randomUUID();
        when(order.getId()).thenReturn(uuid);
        orderDao.placeOrder(order);
        Optional<Order> extractedOrder = orderDao.findById(uuid);
        assertTrue(extractedOrder.isPresent());
    }

}
