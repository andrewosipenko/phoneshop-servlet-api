package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.CartItem;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.DefaultCartService;
import com.es.phoneshop.model.product.exceptions.DeleteException;
import com.es.phoneshop.model.product.exceptions.QuantityLowerZeroException;
import com.es.phoneshop.model.product.exceptions.StockException;
import com.es.phoneshop.model.product.productdao.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class DefaultCartServiceTest {
    private Cart actualCart;
    private CartService cartService;
    private ProductDao productDao;
    private static final String CART_SESSION_ATTRIBUTE = DefaultCartService.class.getName() + ".cart";
    private final Currency usd = Currency.getInstance("USD");
    List<PriceHistory> priceHistoryList = new ArrayList<>();

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

        productDao.saveProduct(new ProductBuilderImpl().setId(0L).setCode("sgs").setDescription("Samsung Galaxy S").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(2L).setCode("sgs3").setDescription("Samsung Galaxy S III").setPrice(new BigDecimal(300)).setCurrency(usd).setStock(5).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(3L).setCode("iphone").setDescription("Apple iPhone").setPrice(new BigDecimal(200)).setCurrency(usd).setStock(10).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(4L).setCode("iphone6").setDescription("Apple iPhone 6").setPrice(new BigDecimal(1000)).setCurrency(usd).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(5L).setCode("htces4g").setDescription("HTC EVO Shift 4G").setPrice(new BigDecimal(320)).setCurrency(usd).setStock(3).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(6L).setCode("sec901").setDescription("Sony Ericsson C901").setPrice(new BigDecimal(420)).setCurrency(usd).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(7L).setCode("xperiaxz").setDescription("Sony Xperia XZ").setPrice(new BigDecimal(120)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(8L).setCode("nokia3310").setDescription("Nokia 3310").setPrice(new BigDecimal(70)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(9L).setCode("palmp").setDescription("Palm Pixi").setPrice(new BigDecimal(170)).setCurrency(usd).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(10L).setCode("simc56").setDescription("Siemens C56").setPrice(new BigDecimal(70)).setCurrency(usd).setStock(20).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(11L).setCode("simc61").setDescription("Siemens C61").setPrice(new BigDecimal(80)).setCurrency(usd).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg").setPriceHistory(priceHistoryList).build());
        productDao.saveProduct(new ProductBuilderImpl().setId(12L).setCode("simsxg75").setDescription("Siemens SXG75").setPrice(new BigDecimal(150)).setCurrency(usd).setStock(40).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg").setPriceHistory(priceHistoryList).build());
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
    public void testGetInstance() {
        CartService cartService1 = DefaultCartService.getInstance();
        CartService cartService2 = DefaultCartService.getInstance();
        assertEquals(cartService1, cartService2);
    }

    @Test
    public void testGetEmptyCart() {
        assertEquals(new Cart(), actualCart);
    }

    @Test
    public void testGetCart() {
        Product product = new ProductBuilderImpl().setId(0L).setCode("sgs").setDescription("Samsung Galaxy S").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg").setPriceHistory(priceHistoryList).build();
        CartItem cartItem = new CartItem(product, 1);
        Cart expectedCart = new Cart();
        expectedCart.getCartItems().add(cartItem);
        actualCart.getCartItems().add(cartItem);
        Cart cart = cartService.getCart(request);
        assertEquals(expectedCart, cart);

    }

    @Test
    public void testAdd() throws StockException {
        cartService.addToCart(actualCart, 0L, 1);
        Product product = new ProductBuilderImpl().setId(0L).setCode("sgs").setDescription("Samsung Galaxy S").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg").setPriceHistory(priceHistoryList).build();
        CartItem cartItem = new CartItem(product, 1);
        assertEquals(actualCart.getCartItems().get(0), cartItem);
    }

    @Test
    public void testAddDouble() throws StockException {
        cartService.addToCart(actualCart, 0L, 1);
        cartService.addToCart(actualCart, 0L, 1);
        Product product = new ProductBuilderImpl().setId(0L).setCode("sgs").setDescription("Samsung Galaxy S").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg").setPriceHistory(priceHistoryList).build();
        CartItem cartItem = new CartItem(product, 2);
        assertEquals(actualCart.getCartItems().get(0), cartItem);
    }

    @Test
    public void testAddNotEnoughStock() {
        try {
            cartService.addToCart(actualCart, 0L, 10000);
            Product product = new ProductBuilderImpl().setId(0L).setCode("sgs").setDescription("Samsung Galaxy S").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg").setPriceHistory(priceHistoryList).build();
            CartItem cartItem = new CartItem(product, 1);
            assertEquals(cartItem, actualCart.getCartItems().get(0));
            fail("Expected ProductNotFindException");
        } catch (StockException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }

    @Test
    public void testGetQuantityOfCartItem() throws StockException {
        cartService.addToCart(actualCart, 0L, 3);
        Product product = new ProductBuilderImpl().setId(0L).setCode("sgs").setDescription("Samsung Galaxy S").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg").setPriceHistory(priceHistoryList).build();
        int expectedQuantity = cartService.getQuantityOfCartItem(actualCart, product);
        assertEquals(expectedQuantity, 3);
    }

    @Test
    public void testGetQuantityOfCartItemWithoutItem() {
        Product product = new ProductBuilderImpl().setId(0L).setCode("sgs").setDescription("Samsung Galaxy S").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg").setPriceHistory(priceHistoryList).build();
        int expectedQuantity = cartService.getQuantityOfCartItem(actualCart, product);
        assertEquals(expectedQuantity, 0);
    }

    @Test
    public void testPutToCart() throws StockException, QuantityLowerZeroException {
        cartService.putToCart(actualCart, 0L, 3);
        Product product = new ProductBuilderImpl().setId(0L).setCode("sgs").setDescription("Samsung Galaxy S").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg").setPriceHistory(priceHistoryList).build();
        int expectedQuantity = cartService.getQuantityOfCartItem(actualCart, product);
        assertEquals(expectedQuantity, 3);
    }

    @Test
    public void testPutToCartWithItemInCart() throws StockException, QuantityLowerZeroException {
        cartService.addToCart(actualCart, 0L, 3);
        cartService.putToCart(actualCart, 0L, 2);
        Product product = new ProductBuilderImpl().setId(0L).setCode("sgs").setDescription("Samsung Galaxy S").setPrice(new BigDecimal(100)).setCurrency(usd).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg").setPriceHistory(priceHistoryList).build();
        int expectedQuantity = cartService.getQuantityOfCartItem(actualCart, product);
        assertEquals(expectedQuantity, 2);
    }

    @Test
    public void testPutToCartErrorNegativeQuantity() throws StockException {
        try {
            cartService.putToCart(actualCart, 0L, -1);
            fail("Expected QuantityLowerZeroException");
        } catch (QuantityLowerZeroException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }

    @Test
    public void testPutToCartErrorStockException() throws QuantityLowerZeroException {
        try {
            cartService.putToCart(actualCart, 0L, 10000);
            fail("Expected StockException");
        } catch (StockException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }

    @Test
    public void testDeleteFromCart() throws StockException, DeleteException {
        cartService.addToCart(actualCart, 0L, 3);
        cartService.deleteFromCart(actualCart, 0L);
        assertEquals(actualCart.getTotalQuantity(), 0);
    }

    @Test
    public void testDeleteFromCartError() {
        try {
            cartService.deleteFromCart(actualCart, 0L);
            fail("Expected DeleteException");
        } catch (DeleteException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }

    @Test
    public void testClear() throws StockException {
        cartService.addToCart(actualCart, 0L, 3);
        cartService.addToCart(actualCart, 3L, 3);
        cartService.clear(actualCart);
        assertEquals(actualCart.getTotalQuantity(), 0);
        assertEquals(actualCart.getCartItems(), new ArrayList<>());
        assertEquals(actualCart.getTotalPrice(), BigDecimal.ZERO);
    }
}