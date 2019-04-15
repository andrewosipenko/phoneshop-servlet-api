package com.es.phoneshop.core.order;

import com.es.phoneshop.core.dao.order.DeliveryMode;
import com.es.phoneshop.core.dao.order.Order;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {

    boolean isOrderValid(Order order);

    Order createOrder(HttpServletRequest request);

    DeliveryMode getDeliveryMode(HttpServletRequest req);
}
