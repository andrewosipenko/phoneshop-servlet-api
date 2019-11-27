package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.IllegalQuantityException;
import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.NoSuchElementException;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @Mock
    private HttpSession httpSession;

    @Mock
    private HttpServletRequest request;

    private Product product;
    private ProductDao productDao;
    private CartService cartService;
    private Cart cart;

    @Before
    public void setup() {
        cartService = HttpSessionCartService.getInstance();
        cart = cartService.getCart(httpSession);
        productDao = ArrayListProductDao.getInstance();
        Currency usd = Currency.getInstance("USD");
        product = new Product(13L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        product.setStock(100);
        productDao.save(product);
        when(request.getSession()).thenReturn(httpSession);
        when(request.getLocale()).thenReturn(new Locale("en", "USA"));
    }

    @Test
    public void testGetExistingCart() {
        Cart cart = new Cart();
        when(httpSession.getAttribute(anyString())).thenReturn(cart);
        Cart cartFromSession = cartService.getCart(httpSession);
        assertEquals(cart, cartFromSession);
    }

    @Test
    public void testGetNotExistingCart() {
        Cart cart = cartService.getCart(httpSession);
        assertNotNull(cart);
    }

    @Test
    public void testAdd() {
        cartService.add(httpSession, cart, product.getId(), String.valueOf(1), request.getLocale());
        assertTrue(cart.getCartItems().size() == 1);
        assertEquals(cart.getCartItems().get(0).getProduct(), product);
        assertEquals(cart.getCartItems().get(0).getQuantity(), 1);
        assertEquals(cart.getTotalQuantity(), 1);
        assertEquals(cart.getTotalPrice(), new BigDecimal(100));
    }

    @Test(expected = LackOfStockException.class)
    public void testAddWithStockLessQuantity() throws LackOfStockException, IllegalQuantityException {
        cartService.add(httpSession, cart, product.getId(), String.valueOf(150), request.getLocale());
    }

    @Test
    public void testAddWithoutSameCartItem() throws LackOfStockException, IllegalQuantityException {
        Cart cart = new Cart();
        cartService.add(httpSession, cart, product.getId(), String.valueOf(8), request.getLocale());
        assertFalse(cart.getCartItems().isEmpty());
    }

    @Test
    public void testAddWithSameCartItem() throws LackOfStockException, IllegalQuantityException {
        CartItem cartItem = new CartItem(product, 1);
        Cart cart = new Cart();
        String quantity = "2";
        cart.getCartItems().add(cartItem);
        cartService.add(httpSession, cart, product.getId(), quantity, request.getLocale());
        assertEquals(cartItem.getQuantity(), (Integer.parseInt(quantity) + 1));
    }

    @Test
    public void testDeleteCartItem() {
        int quantity = 5;
        Cart cart = new Cart();
        cart.getCartItems().add(new CartItem(product, quantity));
        cartService.delete(cart, product);
        assertTrue(cart.getCartItems().isEmpty());
    }
    
    @Test
    public void testCalculateTotalPrice() {
        Cart cart = new Cart();
        cart.getCartItems().add(new CartItem(product, 1));
        cart.getCartItems().add(new CartItem(product, 2));
        cartService.calculateTotalPrice(cart);
        assertEquals(new BigDecimal(300), cart.getTotalPrice());
    }

    @Test
    public void testCalculateTotalQuantity() {
        Cart cart = new Cart();
        cart.getCartItems().add(new CartItem(product, 7));
        cart.getCartItems().add(new CartItem(product, 2));
        cartService.calculateTotalQuantity(cart);
        assertEquals(9, cart.getTotalQuantity());
    }
}

