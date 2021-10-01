package com.es.phoneshop.model.product.order;

import com.es.phoneshop.model.product.exceptions.ItemNotFindException;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ArrayListOrderDao implements OrderDao {

    private List<Order> result;
    private long maxId;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static volatile ArrayListOrderDao instance;

    private ArrayListOrderDao() {
        result = new ArrayList<>();
    }

    public static ArrayListOrderDao getInstance() {
        ArrayListOrderDao result = instance;
        if (result != null) {
            return result;
        }
        synchronized (ArrayListOrderDao.class) {
            if (instance == null) {
                instance = new ArrayListOrderDao();
            }
            return instance;
        }
    }

    @Override
    public Order getOrder(Long id) throws ItemNotFindException {
        lock.readLock().lock();
        try {
            return result.stream()
                    .filter(order -> order.getId().equals(id))
                    .findAny()
                    .orElseThrow(() ->
                            new ItemNotFindException("There is no order with " + id + " id"));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Order getOrderBySecureId(String secureId) throws ItemNotFindException {
        lock.readLock().lock();
        try {
            return result.stream()
                    .filter(order -> order.getSecureId().equals(secureId))
                    .findAny()
                    .orElseThrow(() ->
                            new ItemNotFindException("There is no order with this id"));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public synchronized void saveOrder(Order order) throws ItemNotFindException {
        if (order.getId() == null) {
            order.setId(maxId++);
            result.add(order);
        } else {
            try {
                getOrder(order.getId());
                long productId = order.getId();
                deleteOrder(productId);
                order.setId(productId);
                result.add(order);
            } catch (ItemNotFindException exception) {
                order.setId(maxId++);
                result.add(order);
            }
        }
    }

    @Override
    public synchronized void deleteOrder(Long id) throws ItemNotFindException {
        if (result.stream().anyMatch(getOrder(id)::equals)) {
            result.remove(getOrder(id));
        } else {
            throw new ItemNotFindException("There is no order with " + id + " id");
        }
    }
}