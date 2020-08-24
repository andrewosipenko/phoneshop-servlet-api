package com.es.phoneshop.model.order.dao;

import com.es.phoneshop.model.DAO;
import com.es.phoneshop.model.order.entity.Order;

import java.util.Optional;

public interface OrderDAO extends DAO<Order> {

    Optional<Order> getOrderBySecureId(String secureId);
}
