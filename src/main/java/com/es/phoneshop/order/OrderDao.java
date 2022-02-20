package com.es.phoneshop.order;

import java.util.Optional;

public interface OrderDao {
    Optional<Order> getOrder(Long id);
    Optional<Order> getOrderBySecureId(String secureId);
    void save(Order order);
}
