package com.es.phoneshop.model.order;

import com.es.phoneshop.model.Cart;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private static final OrderService INSTANCE = new OrderServiceImpl();
    private Map<String, Order> orderMap = new HashMap<>();

    private OrderServiceImpl() {}

    public static OrderService getInstance() { return INSTANCE; }

    @Override
    public Order placeOrder(Cart cart) {
        String id = generateId();
        Order order = new Order(id);

        order.setOrderItems(cart.getCartItems()
                .stream()
                .map(cartItem -> new OrderItem(cartItem.getProduct(), cartItem.getQuantity()))
                .collect(Collectors.toList()));

        orderMap.put(id, order);

        return order;
    }

    @Override
    public Order getOrder(String id) {
        return orderMap.get(id);
    }

    private String generateId() {
        String id = java.util.UUID.randomUUID().toString();
        if(orderMap.get(id) == null) {
            return id;
        }
        return generateId();
    }
}