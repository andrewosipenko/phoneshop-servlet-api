package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.GenericDao;
import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.Order;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ArrayListOrderDao extends GenericDao<Order> implements OrderDao {
    ArrayListOrderDao() {
        items = new ArrayList<>();
        id = 0L;
    }

    public static ArrayListOrderDao getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ArrayListOrderDao INSTANCE = new ArrayListOrderDao();
    }

    @Override
    public Order getOrder(Long id) {
        try {
            return get(id);
        } catch (NoSuchElementException e) {
            throw new OrderNotFoundException("Order with ID = " + id + " Not Found");
        }
    }

    @Override
    public Order getOrderBySecureId(String secureId) {
        return lock.read(() -> items.stream()
                .filter(order -> order.getSecureId().equals(secureId))
                .findAny()
                .orElseThrow(() -> new OrderNotFoundException("Order with ID = " + secureId + " Not Found")));
    }
}
