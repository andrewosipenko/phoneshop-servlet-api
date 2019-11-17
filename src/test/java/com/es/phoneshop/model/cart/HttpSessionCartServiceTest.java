package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;

import static junit.framework.TestCase.*;

public class HttpSessionCartServiceTest {
    CartService cartService;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        cartService = HttpSessionCartService.getInstance();
        request = Mockito.mock(HttpServletRequest.class);
        session = Mockito.mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute(CartService.class + ".cart")).thenReturn(new Cart());
    }

    @Test
    public void testEmpty() {
        assertTrue(cartService.getCart(request).getCartItems().isEmpty());
    }

    @Test
    public void testGetCart() {
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg");
        Product product2 = new Product(15L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        Cart cart = cartService.getCart(request);
        cartService.add(cart, product1, 1);
        cartService.add(cart, product2, 1);

        assertNotNull(cart);
        assertEquals(cart.getCartItems().size(), 2);
        assertEquals(cart.getCartItems().get(0).getProduct(), product1);
        assertEquals(cart.getCartItems().get(0).getQuantity(), 1);
        assertEquals(cart.getCartItems().get(1).getProduct(), product2);
        assertEquals(cart.getCartItems().get(1).getQuantity(), 1);
    }

    @Test
    public void testAdd() {
        Cart cart = cartService.getCart(request);

        Currency usd = Currency.getInstance("USD");
        Product product = new Product(15L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        cartService.add(cart, product, 1);

        assertNotNull(cart);
        assertTrue(cart.getCartItems().size() == 1);
        assertEquals(cart.getCartItems().get(0).getProduct(), product);
        assertEquals(cart.getCartItems().get(0).getQuantity(), 1);
        assertEquals(cart.getTotalQuantity(), 1);
        assertEquals(cart.getTotalCost(), new BigDecimal(100));
    }

    @Test
    public void testAddProductTwice() {
        Cart cart = cartService.getCart(request);

        Currency usd = Currency.getInstance("USD");
        Product product = new Product(15L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        cartService.add(cart, product, 1);
        cartService.add(cart, product, 1);

        assertNotNull(cart);
        assertTrue(cart.getCartItems().size() == 1);
        assertEquals(cart.getCartItems().get(0).getProduct(), product);
        assertEquals(cart.getCartItems().get(0).getQuantity(), 2);
        assertEquals(cart.getTotalQuantity(), 2);
        assertEquals(cart.getTotalCost(), new BigDecimal(200));
    }

    @Test(expected = OutOfStockException.class)
    public void addOutOfStock() {
        Cart cart = new Cart();
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(15L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        cartService.add(cart, product, 10000000);
    }


    @Test
    public void testUpdateWithZeroQuantity() {
        Cart cart = cartService.getCart(request);

        Currency usd = Currency.getInstance("USD");
        Product product = new Product(15L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        cartService.add(cart, product, 1);
        cartService.update(cart, product, 0);

        assertNotNull(cart);
        assertTrue(cart.getCartItems().size() == 0);
    }

    @Test
    public void testUpdate() {
        Cart cart = cartService.getCart(request);

        Currency usd = Currency.getInstance("USD");
        Product product = new Product(15L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        cartService.add(cart, product, 1);
        cartService.update(cart, product, 3);

        assertNotNull(cart);
        assertTrue(cart.getCartItems().size() == 1);
        assertEquals(cart.getCartItems().get(0).getProduct(), product);
        assertEquals(cart.getCartItems().get(0).getQuantity(), 3);
        assertEquals(cart.getTotalQuantity(), 3);
        assertEquals(cart.getTotalCost(), new BigDecimal(300));
    }

    @Test
    public void testDelete() {
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg");
        Product product2 = new Product(15L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        Cart cart = cartService.getCart(request);
        cartService.add(cart, product1, 1);
        cartService.add(cart, product2, 1);

        assertNotNull(cart);
        assertEquals(cart.getCartItems().size(), 2);

        cartService.delete(cart, product1);

        assertNotNull(cart);
        assertEquals(cart.getCartItems().size(), 1);
        assertEquals(cart.getCartItems().get(0).getProduct(), product2);
        assertEquals(cart.getCartItems().get(0).getQuantity(), 1);
    }
}
