package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {
    private static OrderService orderService;

    private DefaultOrderService() {
    }

    public static OrderService getInstance() {
        if (orderService == null) {
            synchronized (OrderService.class) {
                if (orderService == null) {
                    orderService = new DefaultOrderService();
                }
            }
        }
        return orderService;
    }

    @Override
    public Order getOrder(Cart cart) {
        Order result;
        if (!cart.getCartItems().isEmpty()) {
            result = new Order(cart.getCartItems().stream()
                    .map(CartItem::new)
                    .collect(Collectors.toList()));
            result.setSubtotalCost(cart.getTotalCost());
            result.setDeliveryCost(getDeliveryCost());
            result.setTotalCost(cart.getTotalCost().add(getDeliveryCost()));
            result.setTotalQuantity(cart.getTotalQuantity());
        } else {
            result = new Order();
        }
        return result;
    }

    @Override
    public String placeOrder(Order order) {
        String secureId = UUID.randomUUID().toString();
        order.setSecureId(secureId);
        ArrayListOrderDao.getInstance().saveOrder(order);
        return secureId;
    }

    @Override
    public Order getOrder(String secureId) {
        return ArrayListOrderDao.getInstance().getOrder(secureId);
    }

    public static BigDecimal getDeliveryCost() {
        return new BigDecimal(10);
    }
}
