package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.*;

public class ArrayListOrderDaoTest {
    OrderDao orderDao;
    Order order;
    String uuid;

    @Before
    public void setUp() throws ParseException {
        orderDao= ArrayListOrderDao.getInstance();

        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg");
        Product product2 = new Product(15L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        CartItem cartItem1= new CartItem(product1, 1);
        CartItem cartItem2=new CartItem(product2, 1);

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);

        order = new Order(cartItems);
        uuid= UUID.randomUUID().toString();
        order.setSecureId(uuid);
        order.setPaymentMethod(PaymentMethod.MONEY);
        order.setDeliveryAddress("Minsk");

        SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy");
        order.setDeliveryDate(format.parse("20.03.2019"));
        order.setPhone("80298700464");
        order.setLastName("Poo");
        order.setFirstName("Do");
        order.setDeliveryCost(new BigDecimal(10));
        order.setTotalQuantity(2);
        order.setSubtotalCost(new BigDecimal(170));
        order.setTotalCost(new BigDecimal(180));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDoubleSaveOrder() {
        orderDao.saveOrder(order);
        orderDao.saveOrder(order);
    }


    @Test
    public void saveOrderSuccessfully() {
        orderDao.saveOrder(order);
        Order result = orderDao.getOrder(uuid);

        assertNotNull(result);
    }

    @Test
    public void testGetOrderSuccessfully() {
        orderDao.saveOrder(order);

        Order result = orderDao.getOrder(uuid);

        assertNotNull(orderDao);
        assertEquals(result.getDeliveryAddress(), order.getDeliveryAddress());
        assertEquals(result.getDeliveryCost(), order.getDeliveryCost());
        assertEquals(result.getDeliveryDate(), order.getDeliveryDate());
        assertEquals(result.getFirstName(), order.getFirstName());
        assertEquals(result.getLastName(), order.getLastName());
        assertEquals(result.getPaymentMethod(), order.getPaymentMethod());
        assertEquals(result.getSecureId(), order.getSecureId());
        assertEquals(result.getPhone(), order.getPhone());
        assertEquals(result.getSubtotalCost(), result.getSubtotalCost());
        assertEquals(result.getTotalQuantity(), order.getTotalQuantity());
        assertEquals(result.getTotalCost(), order.getTotalCost());
    }
}
