package com.es.phoneshop.core.dao.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ArrayListOrderDao implements OrderDao {

    private static ArrayListOrderDao instance;
    private final List<Order> orders;

    private ArrayListOrderDao() {
        orders = new ArrayList<>();
    }

    public static ArrayListOrderDao getInstance() {
        if (instance == null) {
            synchronized (ArrayListOrderDao.class) {
                if (instance == null) {
                    instance = new ArrayListOrderDao();
                }
            }
        }
        return instance;
    }

    @Override
    public void placeOrder(Order order) {
        orders.add(order);
    }


    @Override
    public Optional<Order> findById(UUID orderId) {
        return orders.stream().filter(order -> order.getId().equals(orderId)).findAny();
    }
}
