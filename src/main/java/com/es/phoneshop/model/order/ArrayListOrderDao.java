package com.es.phoneshop.model.order;

import com.es.phoneshop.model.exceptions.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ArrayListOrderDao implements OrderDao {

    private static OrderDao instance;

    private long maxId;
    private List<Order> orders;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public static synchronized OrderDao getInstance() throws OrderNotFoundException {
        if (instance == null) {
            instance = new ArrayListOrderDao();
        }
        return instance;
    }

    private ArrayListOrderDao() {
        this.orders = new ArrayList<>();

    }

    @Override
    public Order getOrder(Long id) throws OrderNotFoundException {
        lock.readLock().lock();
        try {
            return orders.stream()
                    .filter(order -> id.equals(order.getId()))
                    .findAny()
                    .orElseThrow(() -> new OrderNotFoundException());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void save(Order order) {
        lock.writeLock().lock();

        Long id = order.getId();
        try {
            if (id != null) {
                try {
                    Order previousOrder = getOrder(id);
                    orders.set(orders.indexOf(previousOrder), order);
                } catch (OrderNotFoundException e) {
                    order.setId(maxId++);
                    orders.add(order);
                }
            } else {
                order.setId(maxId++);
                orders.add(order);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Order getOrderBuSecureId(String secureId) throws OrderNotFoundException {
        lock.readLock().lock();
        try {
            return orders.stream()
                    .filter(order -> secureId.equals(order.getSecureId()))
                    .findAny()
                    .orElseThrow(() -> new OrderNotFoundException());
        } finally {
            lock.readLock().unlock();
        }
    }
}
