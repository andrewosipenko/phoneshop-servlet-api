package com.es.phoneshop.service;

import com.es.phoneshop.dao.ArrayListOrderDao;
import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.enumeration.PaymentMethod;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultOrderServiceTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private OrderService orderService;
    private CartService cartService;
    private OrderDao orderDao;
    private ProductDao productDao;

    @Before
    public void setup() {
        orderDao = ArrayListOrderDao.getInstance();
        productDao = ArrayListProductDao.getInstance();
        productDao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), Currency.getInstance("USD"), 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        orderService = DefaultOrderService.getInstance();
        cartService = HttpSessionCartService.getInstance();
        Order order = createNewOrder(1L);
        order.setId(1L);
        order.setSecureId("secureId");

        orderDao.save(order);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("cart")).thenReturn(new Cart());
    }


    @Test
    public void testGetOrder() {
        Cart cart = cartService.getCart(request);
        cart.setTotalCost(new BigDecimal(0));

        Order order = orderService.getOrder(cart);

        assertEquals(cart.getItems(), order.getItems());
    }

    @Test
    public void testPlaceOrder() {
        Cart cart = cartService.getCart(request);
        cart.setTotalCost(new BigDecimal(0));

        Order order = orderService.getOrder(cart);
        orderService.placeOrder(order, request);

        assertEquals(cartService.getCart(request), new Cart());
        assertFalse(order.getSecureId().isEmpty());
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