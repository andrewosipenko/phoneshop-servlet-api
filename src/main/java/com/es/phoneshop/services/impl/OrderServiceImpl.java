package com.es.phoneshop.services.impl;

import com.es.phoneshop.dao.ArrayListOrderDao;
import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Customer;
import com.es.phoneshop.model.Order;
import com.es.phoneshop.model.PaymentMethod;
import com.es.phoneshop.services.OrderService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    public final BigDecimal DELIVERY_COST = new BigDecimal(5);
    private static OrderService orderService;
    OrderDao orderDao;

    public static OrderService getInstance() {
        if (orderService == null) {
            orderService = new OrderServiceImpl();
        }
        return orderService;
    }

    public OrderServiceImpl() {
        this.orderDao = new ArrayListOrderDao();
    }


    @Override
    public Order placeOrder(Cart cart, Customer customer, HashMap<String, Object> additionalInformation) {
        Order order = new Order();
        order.setCartItems(cart.getCartItems());
        order.setSubtotalPrice(cart.getPrice());
        order.setDeliveryCost(getDeliveryCost());
        order.setTotalPrice(cart.getPrice().add(getDeliveryCost()));
        order.setSecureId(UUID.randomUUID().toString());
        order.setCustomer(customer);
        order.setDeliveryAddress(additionalInformation.get("deliveryAddress").toString());
        order.setDeliveryDate(additionalInformation.get("deliveryDate").toString(), "DD.MM.yyyy");
        order.setPaymentMethod(PaymentMethod.getByName(additionalInformation.get("paymentMethod").toString()));
        return order;
    }

    @Override
    public BigDecimal getDeliveryCost() {
        return DELIVERY_COST;
    }

    @Override
    public Order getBySecureId(String secureId) {
        return orderDao.getBySecretId(secureId);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderDao.getById(id);
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }


}
