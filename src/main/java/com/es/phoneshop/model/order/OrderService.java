package com.es.phoneshop.model.order;

import com.es.phoneshop.model.Cart;

public interface OrderService{
    Order placeOrder(Cart cart, String name, String address, String phone, Integer orderSum);
    Order getOrder(String orderId);
}
