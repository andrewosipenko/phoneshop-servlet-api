package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.CartItem;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.DefaultCartService;
import com.es.phoneshop.model.product.exceptions.StockException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class DefaultCartServiceTest {
    private Cart actualCart;
    private CartService cartService;
    private ProductDao productDao;
    private static final String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";
    private final Currency usd = Currency.getInstance("USD");

    @Spy
    HttpSession session;
    @Spy
    HttpServletRequest request;

    @Before
    public void setup() throws IllegalAccessException, NoSuchFieldException {
        actualCart = new Cart();
        cartService = DefaultCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();

        session = spy(HttpSession.class);
        when(session.getAttribute(CART_SESSION_ATTRIBUTE)).thenReturn(actualCart);
        request = spy(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);

        resetSingletonCartService();
        resetSingletonArrayListProductDao();

        productDao.saveProduct(new Product(0L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.saveProduct(new Product(1L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.saveProduct(new Product(2L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productDao.saveProduct(new Product(3L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        productDao.saveProduct(new Product(4L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        productDao.saveProduct(new Product(5L, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        productDao.saveProduct(new Product(6L, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        productDao.saveProduct(new Product(7L, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        productDao.saveProduct(new Product(8L, "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        productDao.saveProduct(new Product(9L, "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        productDao.saveProduct(new Product(10L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        productDao.saveProduct(new Product(11L, "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        productDao.saveProduct(new Product(12L, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
    }

    public void resetSingletonCartService() throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = DefaultCartService.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    public void resetSingletonArrayListProductDao() throws SecurityException,
            NoSuchFieldException,
            IllegalArgumentException,
            IllegalAccessException {
        Field instance = ArrayListProductDao.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void getInstanceTest() {
        CartService cartService1 = DefaultCartService.getInstance();
        CartService cartService2 = DefaultCartService.getInstance();
        assertEquals(cartService1, cartService2);
    }

    @Test
    public void getEmptyCartTest() {
        assertEquals(new Cart(), actualCart);
    }

    @Test
    public void getCartTest() {
        Product product = new Product(0L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        CartItem cartItem = new CartItem(product, 1);
        Cart expectedCart = new Cart();
        expectedCart.getCartItems().add(cartItem);
        actualCart.getCartItems().add(cartItem);
        Cart cart = cartService.getCart(request);
        assertEquals(expectedCart, cart);

    }

    @Test
    public void addTest() throws StockException {
        cartService.addToCart(actualCart, 0L, 1);
        Product product = new Product(0L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        CartItem cartItem = new CartItem(product, 1);
        assertEquals(actualCart.getCartItems().get(0), cartItem);
    }

    @Test
    public void addDoubleTest() throws StockException {
        cartService.addToCart(actualCart, 0L, 1);
        cartService.addToCart(actualCart, 0L, 1);
        Product product = new Product(0L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        CartItem cartItem = new CartItem(product, 2);
        assertEquals(actualCart.getCartItems().get(0), cartItem);
    }

    @Test
    public void addNotEnoughStockTest() {
        try {
            cartService.addToCart(actualCart, 0L, 10000);
            Product product = new Product(0L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
            CartItem cartItem = new CartItem(product, 1);
            assertEquals(cartItem, actualCart.getCartItems().get(0));
            fail("Expected ProductNotFindException");
        } catch (StockException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }
}
