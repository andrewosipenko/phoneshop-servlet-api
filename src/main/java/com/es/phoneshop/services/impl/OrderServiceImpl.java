package com.es.phoneshop.services.impl;

import com.es.phoneshop.dao.ArrayListOrderDao;
import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Customer;
import com.es.phoneshop.model.Order;
import com.es.phoneshop.services.OrderService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    public final BigDecimal DELIVERY_COST = new BigDecimal(5);
    OrderDao orderDao;
    ProductDao productDao;

    private static volatile OrderServiceImpl orderService;

    public static OrderServiceImpl getInstance() {
        OrderServiceImpl localInstance = orderService;
        if (localInstance == null) {
            synchronized (OrderServiceImpl.class) {
                localInstance = orderService;
                if (localInstance == null) {
                    orderService = localInstance = new OrderServiceImpl();
                }
            }
        }
        return orderService;
    }

    private OrderServiceImpl() {
        this.productDao = ArrayListProductDao.getInstance();
        this.orderDao = ArrayListOrderDao.getInstance();
    }


    @Override
    public Order generateOrder(Cart cart, Customer customer, HashMap<String, String> additionalInformation) {
        Order order = new Order();
        order.setCartItems(cart.getCartItems());
        order.setSubtotalPrice(cart.getPrice());
        order.setDeliveryCost(getDeliveryCost());
        order.setTotalPrice(cart.getPrice().add(getDeliveryCost()));
        order.setSecureId(UUID.randomUUID().toString());
        order.setCustomer(customer);
        order.setDeliveryAddress(additionalInformation.get("deliveryAddress"));
        order.setDeliveryDate(additionalInformation.get("deliveryDate"), "DD.MM.yyyy");
        order.setPaymentMethod(PaymentMethod.getByName(additionalInformation.get("paymentMethod")));
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
    public void placeOrder(Order order) {
        order.getCartItems()
                .forEach(cartItem -> productDao.reduceAmountProducts(cartItem.getProduct(), cartItem.getQuantity()));
        save(order);
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }


}
