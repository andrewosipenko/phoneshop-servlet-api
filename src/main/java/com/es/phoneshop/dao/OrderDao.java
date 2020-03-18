package com.es.phoneshop.dao;

import com.es.phoneshop.order.Order;

public interface OrderDao extends Dao<Order> {
    Order getOrder(String id);
}
