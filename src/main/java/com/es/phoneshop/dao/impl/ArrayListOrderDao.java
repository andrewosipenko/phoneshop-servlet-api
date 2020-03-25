package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.order.Order;

import java.util.ArrayList;

public class ArrayListOrderDao extends AbstractDao<Order> implements OrderDao {
    private ArrayListOrderDao() {
        super.items = new ArrayList<>();
    }

    public static ArrayListOrderDao getInstance() {
        return ArrayListOrderDao.OrderDaoHolder.instance;
    }

    private static class OrderDaoHolder {
        private static final ArrayListOrderDao instance = new ArrayListOrderDao();
    }

    @Override
    public Order getOrder(String id) {
        return super.items.stream()
                .filter(order -> order.getSecureId().equals(id))
                .findAny().get();
    }
}
