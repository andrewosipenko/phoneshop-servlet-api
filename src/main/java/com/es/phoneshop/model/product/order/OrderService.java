package com.es.phoneshop.model.product.order;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.enums.payment.PaymentMethod;

import java.util.List;

public interface OrderService {
    Order getOrder(Cart cart);

    List<PaymentMethod> getPaymentMethod();

    void placeOrder(Order order);
}