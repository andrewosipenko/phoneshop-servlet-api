package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

public interface OrderService {
    Order getOrder(Cart cart);

    String placeOrder(Order order);

    Order getOrder(String secureId);
}
