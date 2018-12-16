package com.es.phoneshop.model.order;

import java.util.ArrayList;
import java.util.List;

public class ArrayListOrderDao implements OrderDao{

    private static List<Order> orders = new ArrayList<>();
    private long maxId = 0;

    private static volatile ArrayListOrderDao instance;

    private ArrayListOrderDao() {
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
    public Order getOrder(Long id) {
        return orders
                .stream()
                .filter(product1 -> product1.getId().equals(id))
                .findFirst().orElseThrow(()-> new NoSuchOrderException(id));
    }

    @Override
    public void save(Order order) {
        Long id = order.getId();
        if (orders.stream()
                .noneMatch(product1 -> product1.getId()
                        .equals(order.getId()))) {
            order.setId(maxId++);
            orders.add(order);
        } else {
            throw new RuntimeException("Order with id " + id + " already exists.");
        }
    }
}
