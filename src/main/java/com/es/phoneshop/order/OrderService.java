package com.es.phoneshop.order;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private static OrderService orderService;
    private final List<Order> orders = new ArrayList<>();

    private OrderService(){}

    public static OrderService getInstance(){
        if(orderService == null){
            synchronized(OrderService.class){
                if(orderService == null){
                    orderService = new OrderService();
                }
            }
        }
        return orderService;
    }

    public void addOrder(Order order){
        if(order != null && validCheck(order)){
            orders.add(order);
        }
    }

    public boolean validCheck(Order order){
        if (!order.getName().matches("[a-zA-Z]*")) {
            return false;
        }
        if (!order.getLastName().matches("[a-zA-Z]*")) {
            return false;
        }
        if (!order.getPhoneNumber().matches("\\+\\d{12}")) {
            return false;
        }
        return true;
    }


}
