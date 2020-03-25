package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.impl.ArrayListOrderDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.order.Order;
import com.es.phoneshop.service.OrderService;

import java.math.BigDecimal;
import java.util.UUID;

public class DefaultOrderService implements OrderService {
    private OrderDao orderDao;

    private DefaultOrderService() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    public static DefaultOrderService getInstance() {

        return OrderServiceHolder.instance;
    }

    private static class OrderServiceHolder {
        private static final DefaultOrderService instance = new DefaultOrderService();
    }

    @Override
    public Order getOrder(Cart cart) {
        Order order = new Order(cart);
        order.setSubtotal(cart.getTotalPrice());
        order.setDeliveryCost(new BigDecimal(5));
        order.setTotalPrice(order.getDeliveryCost().add(order.getSubtotal()));
        return order;
    }

    @Override
    public String placeOrder(Order order) {
        String id = UUID.randomUUID().toString();
        order.setId(id);
        orderDao.save(order);
        return id;
    }

    @Override
    public Order getBySecureId(String secureId) {
        return orderDao.getOrder(secureId);
    }
}
