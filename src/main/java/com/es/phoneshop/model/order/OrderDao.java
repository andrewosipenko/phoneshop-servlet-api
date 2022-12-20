package com.es.phoneshop.model.order;

import com.es.phoneshop.model.exceptions.OrderNotFoundException;

public interface OrderDao {
    Order getOrder(Long id) throws OrderNotFoundException;

    void save(Order order) throws OrderNotFoundException;

    Order getOrderBuSecureId(String secureId) throws OrderNotFoundException;
}
