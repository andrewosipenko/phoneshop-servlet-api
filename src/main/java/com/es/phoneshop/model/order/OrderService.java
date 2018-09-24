package com.es.phoneshop.model.order;

import com.es.phoneshop.model.Cart;

public interface OrderService{
    Order placeOrder(Cart cart);
    Order getOrder(String id);
}