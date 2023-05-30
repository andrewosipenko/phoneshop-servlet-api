package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Order;
import com.es.phoneshop.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
    @Mock
    private OrderDao orderDao;
    @InjectMocks
    private OrderServiceImpl orderService;
    private Cart cart;
    private static final String SECURED_ID = "1";

    @Before
    public void setup() {
        cart = new Cart();
        Currency usd = Currency.getInstance("USD");
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"), 1));
        cartItems.add(new CartItem(new Product(2L,"sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"), 2));
        cart.setCartItems(cartItems);
        cart.setTotalQuantity(3);
        cart.setTotalCost(BigDecimal.valueOf(400));
    }

    @Test
    public void testGetOrder() {
        Order foundOrder = orderService.getOrder(cart);

        BigDecimal expectedSubtotal = BigDecimal.valueOf(400);
        int expectedQuantity = 3;
        assertEquals(expectedSubtotal, foundOrder.getSubtotal());
        assertEquals(expectedQuantity, foundOrder.getTotalQuantity());
    }

    @Test
    public void testPlaceOrder() {
        Order order = new Order();
        order.setSecureId(SECURED_ID);

        orderService.placeOrder(order);

        verify(orderDao).save(any());
    }
}
