package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static junit.framework.TestCase.*;

public class DefaultOrderServiceTest {

    OrderService orderService;
    OrderDao orderDao;
    Order order;
    String uuid;
    Cart cart;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        orderService=DefaultOrderService.getInstance();
        orderDao=ArrayListOrderDao.getInstance();

        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg");
        Product product2 = new Product(15L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        CartItem cartItem1= new CartItem(product1, 1);
        CartItem cartItem2=new CartItem(product2, 1);

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);

        cart=new Cart();
        cart.setCartItems(cartItems);
        cart.setTotalQuantity(2);
        cart.setTotalCost(new BigDecimal(170));

        order = new Order(cartItems);
        uuid=UUID.randomUUID().toString();
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

    @Test
    public void testGetOrderByUUIDSuccessfully() {
       orderDao.saveOrder(order);

       Order result = orderService.getOrder(uuid);

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

    @Test
    public void testPlaceOrderSuccessfully() {
        String resultUUID = orderService.placeOrder(order);

        assertNotNull(resultUUID);
    }

    @Test
    public void testGetOrderByCartSuccessfully(){
        Order result = orderService.getOrder(cart);

        assertNotNull(result);
        assertEquals(result.getTotalCost(), cart.getTotalCost().add(DefaultOrderService.getDeliveryCost()));
        assertEquals(result.getDeliveryCost(), DefaultOrderService.getDeliveryCost());
        assertEquals(result.getSubtotalCost(), cart.getTotalCost());
        assertEquals(result.getTotalQuantity(), cart.getTotalQuantity());
    }

    @Test
    public void testGetOrderByEmptyCard(){
        Cart cart = new Cart();
        Order result = orderService.getOrder(cart);

        assertEquals(result.getTotalQuantity(), 0);
        assertNull(result.getDeliveryAddress());
        assertNull(result.getDeliveryCost());
        assertNull(result.getDeliveryDate());
        assertNull(result.getFirstName());
        assertNull(result.getLastName());
        assertNull(result.getPaymentMethod());
        assertNull(result.getSecureId());
        assertNull(result.getPhone());
        assertNull(result.getSubtotalCost());
        assertNull(result.getTotalCost());
        assertEquals(result.getCartItems().size(), 0);
    }
}
