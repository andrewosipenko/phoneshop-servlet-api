package com.es.phoneshop.service;

import com.es.phoneshop.dao.ArrayListOrderDao;
import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.enumeration.PaymentMethod;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.Order;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {
    private OrderDao orderDao;
    private CartService cartService;

    private DefaultOrderService() {
        orderDao = ArrayListOrderDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    private static OrderService instance;

    public static synchronized OrderService getInstance() {
        if (instance == null) {
            instance = new DefaultOrderService();
        }
        return instance;
    }

    @Override
    public Order getOrder(Cart cart) {
        Order order = new Order();

        order.setItems(cart.getItems().stream().map(item -> {
                    try {
                        return (CartItem) item.clone();
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList())
        );

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
    public void placeOrder(Order order, HttpServletRequest request) {
        order.setSecureId(UUID.randomUUID().toString());
        cartService.clearCart(request);
        orderDao.save(order);
    }

    private BigDecimal calculateDeliveryCost() {
        return new BigDecimal(10);
    }
}
