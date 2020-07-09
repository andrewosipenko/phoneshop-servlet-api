package com.es.phoneshop.services;

import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Customer;
import com.es.phoneshop.model.Order;

import java.math.BigDecimal;
import java.util.HashMap;

public interface OrderService {
    Order placeOrder(Cart cart, Customer customer, HashMap<String, Object> additionalInformation);

    BigDecimal getDeliveryCost();

    Order getBySecureId(String secureId);

    Order getOrderById(Long id);

    void save(Order order);
}
