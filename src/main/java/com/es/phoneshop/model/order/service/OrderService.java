package com.es.phoneshop.model.order.service;

import com.es.phoneshop.model.cart.entity.Cart;
import com.es.phoneshop.model.order.entity.Order;
import com.es.phoneshop.model.order.entity.PaymentMethod;
import com.es.phoneshop.model.product.entity.Product;

import java.util.List;
import java.util.NoSuchElementException;

public interface OrderService {

    Order getOrder(Long id);

    Order getOrder(Cart cart);

    List<PaymentMethod> getPaymentMethods();

    void placeOrder(Order order);

    Order getOrderBySecureId(String secureOrderId);
}
