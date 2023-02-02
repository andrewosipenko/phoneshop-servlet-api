package com.es.phoneshop.dao;

import com.es.phoneshop.enumeration.PaymentMethod;
import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ArrayListOrderDaoTest {
    private OrderDao orderDao;
    private ProductDao productDao;

    @Before
    public void setup() {
        orderDao = ArrayListOrderDao.getInstance();
        productDao = ArrayListProductDao.getInstance();
        productDao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), Currency.getInstance("USD"), 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        Order order = createNewOrder(1L);
        order.setId(1L);
        order.setSecureId("secureId");

        orderDao.save(order);
    }

    @Test
    public void testGetOrder() {
        Order order = orderDao.findById(1L);

        assertEquals(createNewOrder(1L), order);
    }

    @Test(expected = OrderNotFoundException.class)
    public void testGetOrderWithIncorrectId() {
        orderDao.findById(2L);
    }

    @Test
    public void testGetOrderBySecureId() {
        Order order = orderDao.findBySecureId("secureId");

        assertEquals(createNewOrder(1L), order);
    }

    @Test(expected = OrderNotFoundException.class)
    public void testGetOrderBySecureIdWithIncorrectId() {
       orderDao.findBySecureId("anotherSecureId");
    }

    private Order createNewOrder(Long orderId) {
        Order order = new Order();
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem(productDao.findById(1L), 1);
        cartItems.add(cartItem);
        order.setItems(cartItems);
        if (orderId != null) {
            order.setId(orderId);
        }
        order.setSecureId(UUID.randomUUID().toString());
        order.setPhone("+375447626615");
        order.setFirstName("Anton");
        order.setLastName("Ahinski");
        order.setDeliveryDate(LocalDate.of(2023, 10, 10));
        order.setDeliveryAddress("sdjgak");
        order.setPaymentMethod(PaymentMethod.CASH);
        return order;
    }
}