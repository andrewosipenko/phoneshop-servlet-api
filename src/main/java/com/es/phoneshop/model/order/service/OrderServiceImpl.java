package com.es.phoneshop.model.order.service;

import com.es.phoneshop.model.GenericArrayListDao;
import com.es.phoneshop.model.cart.entity.Cart;
import com.es.phoneshop.model.cart.entity.CartItem;
import com.es.phoneshop.model.cart.service.CartService;
import com.es.phoneshop.model.order.dao.ArrayListOrderDao;
import com.es.phoneshop.model.order.entity.Order;
import com.es.phoneshop.model.order.entity.PaymentMethod;
import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.web.exceptions.OutOfStockException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public enum OrderServiceImpl implements OrderService {

    INSTANCE;

    private final GenericArrayListDao<Order> orderDao = ArrayListOrderDao.getInstance();

    @Override
    public Order getOrder(Long id) {
        try {
            return orderDao.getItem(id).get();
        } catch (NoSuchElementException e) {
            //todo refactor errorItemNotFound.jsp to show info not only about product
            throw new NoSuchElementException("Order with id " + id + "not found");
        }
    }

    @Override
    public Order getOrder(Cart cart) {
        Order order = new Order();
        order.setCartItems(cart.getItems().stream().map(cartItem -> {
            try {
                return ((CartItem) cartItem.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList()));
        order.setSubtotal(cart.getTotalCost());
        order.setDeliveryCost(calculateDeliveryCost());
        order.setTotalCost(order.getSubtotal().add(order.getDeliveryCost()));
        return order;
    }

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return List.of(PaymentMethod.values());
    }

    @Override
    public void placeOrder(Order order) {
        orderDao.save(order);
    }

    private BigDecimal calculateDeliveryCost() {
        return new BigDecimal(5);
    }
}
