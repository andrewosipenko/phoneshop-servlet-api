package com.es.phoneshop.model.product.service;

import com.es.phoneshop.model.product.entity.Cart;
import com.es.phoneshop.model.product.entity.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.function.Consumer;

public interface OrderService {
    Order createOrderFromCart(Cart example);

    void setRequiredStringCustomerInfo(Order order,
                                       String requiredParameter,
                                       HttpServletRequest request,
                                       Map<String, String> errorsMap,
                                       Consumer<String> consumer);

    void setRequiredPhoneNumberCustomerInfo(
            Order order,
            HttpServletRequest request,
            Map<String, String> errorsMap);

    void setRequiredLocalDateCustomerInfo(
            Order order,
            HttpServletRequest request,
            Map<String, String> errorsMap);

    void setRequiredPaymentMethodCustomerInfo(
            Order order,
            HttpServletRequest request,
            Map<String, String> errorsMap);

    void placeOrder(Order orderToPlace, HttpServletRequest request);

    String parseSecureOrderIdFromRequest(HttpServletRequest request);
}
