package com.es.phoneshop.model.product.dao.order;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

public interface OrderDao {
    void placeOrder(Order order);

    Order getOrder(HttpServletRequest request);

    Optional<Order> findById(UUID orderId);

    boolean isOrderValid(Order order);
}
