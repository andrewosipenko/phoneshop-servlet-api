package com.es.phoneshop.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ArrayListOrderDao implements OrderDao {
    private static OrderDao instance;
    private static final Object mutex = new Object();
    private long maxId;
    private final List<Order> orders;
    private final ReadWriteLock rwLock;

    public static OrderDao getInstance() {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new ArrayListOrderDao();
                }
            }
        }
        return instance;
    }

    private ArrayListOrderDao() {
        orders = new ArrayList<>();
        rwLock = new ReentrantReadWriteLock();
    }

    @Override
    public void save(Order order) {
        if (order != null) {
            rwLock.writeLock().lock();
            try {
                if (order.getId() != null) {
                    for (int i = 0; i < orders.size(); i++) {
                        if (order.getId().equals(orders.get(i).getId())) {
                            orders.set(i, order);
                            break;
                        }
                    }
                } else {
                    order.setId(maxId++);
                    orders.add(order);
                }
            } finally {
                rwLock.writeLock().unlock();
            }
        }
    }

    @Override
    public Optional<Order> getOrderBySecureId(String secureId) {
        if (secureId == null) {
            return Optional.empty();
        } else {
            rwLock.readLock().lock();
            try {
                return orders.stream()
                        .filter(order -> secureId.equals(order.getSecureId()))
                        .findAny();
            } finally {
                rwLock.readLock().unlock();
            }
        }
    }
}
