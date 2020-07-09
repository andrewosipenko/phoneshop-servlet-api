package com.es.phoneshop.dao;

import com.es.phoneshop.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayListOrderDao extends AbstractDefaultDao<Order> implements OrderDao {
    private static OrderDao orderDao = new ArrayListOrderDao();
    private List<Order> orderList = new ArrayList<>();

    public ArrayListOrderDao(List<Order> items) {
        this.orderList = items;
        super.init(items);
    }

    public static OrderDao getInstance() {
        return orderDao;
    }

    public ArrayListOrderDao() {
        super.init(this.orderList);
    }

    @Override
    public Order getBySecretId(String secretKey) {
        if (secretKey == null) {
            new IllegalArgumentException();
        }

        return this.orderList.stream()
                .filter(order -> order.getSecureId().equals(secretKey))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}