package com.es.phoneshop.service.impl;

import com.es.phoneshop.FunctionalReadWriteLock;
import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.impl.ArrayListOrderDao;
import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Order;
import com.es.phoneshop.service.OrderService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = ArrayListOrderDao.getInstance();
    private final FunctionalReadWriteLock lock;

    private OrderServiceImpl() {
        lock = new FunctionalReadWriteLock();
    }

    public static OrderServiceImpl getInstance() {
        return OrderServiceImpl.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final OrderServiceImpl INSTANCE = new OrderServiceImpl();
    }

    @Override
    public Order getOrder(Cart cart) {
        Order order = new Order();
        order.setCartItems(cart.getCartItems().stream()
                .map(cartItem -> (CartItem) cartItem.clone())
                .collect(Collectors.toList()));
        order.setTotalQuantity(cart.getTotalQuantity());
        order.setSubtotal(cart.getTotalCost());
        order.setDeliveryCost(calculateDeliveryCost());
        order.setTotalCost(order.getSubtotal().add(order.getDeliveryCost()));
        return order;
    }

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }

    @Override
    public void placeOrder(Order order) {
        order.setSecureId(UUID.randomUUID().toString());
        orderDao.save(order);
    }

    private BigDecimal calculateDeliveryCost() {
        return new BigDecimal(5);
    }
}
