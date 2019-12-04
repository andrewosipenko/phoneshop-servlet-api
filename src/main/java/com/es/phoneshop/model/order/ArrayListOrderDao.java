package com.es.phoneshop.model.order;

import java.util.ArrayList;
import java.util.List;

public class ArrayListOrderDao implements OrderDao {
    private static OrderDao orderDao;

    public static OrderDao getInstance() {
        if (orderDao == null) {
            synchronized (ArrayListOrderDao.class) {
                if (orderDao == null) {
                    orderDao = new ArrayListOrderDao();
                }
            }
        }
        return orderDao;
    }

    private List<Order> orders = new ArrayList<>();

    @Override
    public void saveOrder(Order order) {
        if (orders.contains(order))
            throw new IllegalArgumentException();
        if (order != null)
            orders.add(order);
    }

    @Override
    public Order getOrder(String secureId) {
        return orders.stream()
                .filter(order -> secureId.equals(order.getSecureId()))
                .findFirst()
                .get();
    }
}
