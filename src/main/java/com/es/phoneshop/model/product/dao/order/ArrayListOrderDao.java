package com.es.phoneshop.model.product.dao.order;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.HttpSessionCartService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ArrayListOrderDao implements OrderDao {

    private static ArrayListOrderDao INSTANCE;
    private final CartService cartService;
    private final List<Order> orders;

    private ArrayListOrderDao() {
        orders = new ArrayList<>();
        cartService = HttpSessionCartService.getInstance();
    }

    public static ArrayListOrderDao getInstance() {
        if (INSTANCE == null) {
            synchronized (ArrayListOrderDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ArrayListOrderDao();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void placeOrder(Order order) {
        orders.add(order);
    }

    @Override
    public Order getOrder(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phone");
        String deliveryDate = req.getParameter("deliveryDate");
        String deliveryAddress = req.getParameter("deliveryAddress");
        DeliveryMode deliveryMode = DeliveryMode.identify(req.getParameter("deliveryMode"));
        PaymentMethod paymentMethod = PaymentMethod.identify(req.getParameter("paymentMethod"));
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
    public Optional<Order> findById(UUID orderId) {
        return orders.stream().filter(order -> order.getId().equals(orderId)).findAny();
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
