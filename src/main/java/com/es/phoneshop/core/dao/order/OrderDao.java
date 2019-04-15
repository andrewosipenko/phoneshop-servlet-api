package com.es.phoneshop.core.dao.order;

import java.util.Optional;
import java.util.UUID;

public interface OrderDao {
    void placeOrder(Order order);

    Optional<Order> findById(UUID orderId);
}
