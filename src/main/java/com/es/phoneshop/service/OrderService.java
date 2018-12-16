package com.es.phoneshop.service;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;

public interface OrderService {
    Order placeOrder(Cart cart, String name, String deliveryAddress, String phone);
}
