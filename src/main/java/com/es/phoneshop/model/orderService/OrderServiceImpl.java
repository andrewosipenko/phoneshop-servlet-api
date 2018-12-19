package com.es.phoneshop.model.orderService;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private List<Order> orders;

    private static volatile OrderService orderService;

    private OrderServiceImpl() {
        orders = new ArrayList<>();
    }

    public static OrderService getInstance() {
        if (orderService == null) {
            synchronized (OrderServiceImpl.class) {
                if (orderService == null) {
                    orderService = new OrderServiceImpl();
                }
            }
        }
        return orderService;
    }

    @Override
    public Order placeOrder(Cart cart, String name, String deliveryAddress, String phone) {
        Order order = new Order();
        order.setName(name);
        order.setDeliveryAddress(deliveryAddress);
        order.setPhone(phone);
        order.getCartItems().addAll(cart.getCartItems());
        ArrayListOrderDao.getInstance().save(order);
        return order;
    }
}
