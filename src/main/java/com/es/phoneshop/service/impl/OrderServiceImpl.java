package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.ArrayListOrderDao;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.OrderService;

public class OrderServiceImpl implements OrderService {

    private static volatile OrderService orderService;

    private OrderServiceImpl(){}

    public static OrderService getInstance(){
        if(orderService == null)
            synchronized (CartServiceImpl.class){
                if(orderService == null)
                    orderService = new OrderServiceImpl();
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
