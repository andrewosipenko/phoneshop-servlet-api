package com.es.phoneshop.order;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartItem;
import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.cart.DefaultCartService;
import com.es.phoneshop.lock.SessionLock;
import com.es.phoneshop.lock.SessionLockService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {
    private static OrderService instance;
    private static final Object mutex = new Object();
    private final SessionLock lock;
    private OrderDao orderDao;
    private CartService cartService;

    public static OrderService getInstance() {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new DefaultOrderService();
                }
            }
        }
        return instance;
    }

    private DefaultOrderService() {
        orderDao = ArrayListOrderDao.getInstance();
        cartService = DefaultCartService.getInstance();
        lock = SessionLockService.getInstance();
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
        }).collect(Collectors.toList()));
        order.setSubtotal(cart.getTotalCost());
        order.setDeliveryCost(calculateDeliveryCost());
        order.setTotalCost(order.getSubtotal().add(order.getDeliveryCost()));
        return order;
    }

    private BigDecimal calculateDeliveryCost() {
        return new BigDecimal(5);
    }

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }

    @Override
    public void placeOrder(HttpServletRequest request, Order order) {
        lock.getSessionLock(request).lock();
        try {
            order.setSecureId(UUID.randomUUID().toString());
            orderDao.save(order);
            cartService.clearCart(request);
        } finally {
            if (lock.getSessionLock(request).isHeldByCurrentThread()) {
                lock.getSessionLock(request).unlock();
            }
        }
    }
}
