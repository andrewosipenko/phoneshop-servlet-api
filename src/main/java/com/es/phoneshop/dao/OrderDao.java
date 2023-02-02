package com.es.phoneshop.dao;

import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;

import java.util.List;

public interface OrderDao {
    Order findById(Long id);
    Order findBySecureId(String id) throws OrderNotFoundException;
    List<Order> findAll();
    void save(Order order);
    void delete(Long id);
}
