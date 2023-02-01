package com.es.phoneshop.model.product.dao.impl;

import com.es.phoneshop.model.product.dao.EntityDao;
import com.es.phoneshop.model.product.dao.OrderDao;
import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.entity.Order;
import com.es.phoneshop.model.product.exception.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ArrayListOrderDao extends EntityDao<Order> implements OrderDao {
    private static volatile OrderDao instance;

    public static OrderDao getInstance() {
        OrderDao localInstance = instance;
        if (localInstance == null) {
            synchronized (ProductDao.class) {
                localInstance = instance;
                if (localInstance == null)
                    instance = localInstance = new ArrayListOrderDao();
            }
        }
        return localInstance;
    }

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    private long currentMaxId = 1;


    private ArrayListOrderDao() {
        entities = new ArrayList<>();
    }

    @Override
    public Order getOrderBySecureId(String secureId) throws OrderNotFoundException {
        if (secureId == null) {
            throw new IllegalArgumentException("Parameter id is null");
        }
        lock.readLock().lock();
        try {
            Order order = entities.stream().
                    filter(currOrder -> secureId.equals(currOrder.getSecureId())).
                    findAny().
                    orElseThrow(() -> new OrderNotFoundException(String.format("No such order with given code:%s", secureId)));
            return order;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    protected final void addNewEntity(Order entity, List<Order> entities) {
        entity.setId(currentMaxId++);
        entities.add(entity);
    }
}
