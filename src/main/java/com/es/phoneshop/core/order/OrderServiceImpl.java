package com.es.phoneshop.core.order;

import com.es.phoneshop.core.cart.Cart;
import com.es.phoneshop.core.cart.CartService;
import com.es.phoneshop.core.cart.HttpSessionCartService;
import com.es.phoneshop.core.dao.order.DeliveryMode;
import com.es.phoneshop.core.dao.order.Order;
import com.es.phoneshop.core.dao.order.PaymentMethod;

import javax.servlet.http.HttpServletRequest;

public class OrderServiceImpl implements OrderService {
    private static OrderServiceImpl instance;
    private CartService cartService;

    private OrderServiceImpl() {
        cartService = HttpSessionCartService.getInstance();
    }

    public static OrderServiceImpl getInstance() {
        if (instance == null) {
            synchronized (OrderServiceImpl.class) {
                if (instance == null) {
                    instance = new OrderServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Order createOrder(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phone");
        String deliveryDate = req.getParameter("deliveryDate");
        String deliveryAddress = req.getParameter("deliveryAddress");
        DeliveryMode deliveryMode = DeliveryMode.valueOf(req.getParameter("deliveryMode").replaceAll(" ", "_").toUpperCase());
        PaymentMethod paymentMethod = PaymentMethod.valueOf(req.getParameter("paymentMethod").replaceAll(" ", "_").toUpperCase());
        Cart cart = cartService.getCart(req);
        return new Order(
                cart,
                firstName,
                lastName,
                phone,
                deliveryDate,
                deliveryAddress,
                deliveryMode,
                paymentMethod
        );
    }

    @Override
    public DeliveryMode getDeliveryMode(HttpServletRequest req) {
        String deliveryMode;
        if ((deliveryMode = req.getParameter("deliveryMode")) == null) {
            return DeliveryMode.COURIER;
        } else {
            return DeliveryMode.valueOf(deliveryMode.replaceAll(" ", "_").toUpperCase());
        }
    }

    @Override
    public boolean isOrderValid(Order order) {
        return !order.getFirstName().isEmpty()
                && !order.getLastName().isEmpty()
                && !order.getPhone().isEmpty()
                && !order.getDeliveryAddress().isEmpty()
                && !order.getDeliveryDate().isEmpty();
    }

}
