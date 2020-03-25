package com.es.phoneshop.service;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.order.Order;

public interface OrderService {
    Order getOrder(Cart cart);

    Order getBySecureId(String secureId);

    String placeOrder(Order order);
}
