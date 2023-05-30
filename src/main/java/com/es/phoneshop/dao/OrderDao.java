package com.es.phoneshop.dao;

import com.es.phoneshop.model.Order;

public interface OrderDao {
    Order getOrder(Long id);

    Order getOrderBySecureId(String secureId);

    void save(Order order);

    void delete(Long id);
}
