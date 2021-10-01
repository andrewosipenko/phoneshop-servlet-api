package com.es.phoneshop.model.product.order;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.CartItem;
import com.es.phoneshop.model.product.enums.payment.PaymentMethod;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

    private ArrayListOrderDao orderDao;
    private static volatile DefaultOrderService instance;

    private DefaultOrderService() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    public static DefaultOrderService getInstance() {
        DefaultOrderService cart = instance;
        if (cart != null) {
            return cart;
        }
        synchronized (DefaultOrderService.class) {
            if (instance == null) {
                instance = new DefaultOrderService();
            }
            return instance;
        }
    }


    @Override
    public synchronized Order getOrder(Cart cart) {
        Order order = new Order();
        order.setCartItems(cart.getCartItems().stream()
                .map(cartItem -> {
                    try {
                        return (CartItem) cartItem.clone();
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList()));
        order.setSubtotalPrice(cart.getTotalPrice());
        order.setDeliveryPrice(calculateDeliveryPrice());
        order.setTotalPrice(cart.getTotalPrice().add(calculateDeliveryPrice()));
        order.setTotalQuantity(cart.getTotalQuantity());
        return order;
    }

    private BigDecimal calculateDeliveryPrice() {
        return new BigDecimal(10);
    }

    @Override
    public List<PaymentMethod> getPaymentMethod() {
        return Arrays.asList(PaymentMethod.values());
    }

    @Override
    public void placeOrder(Order order) {
        orderDao.saveOrder(order);
    }
}