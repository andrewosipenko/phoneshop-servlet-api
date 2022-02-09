package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {
    private Lock lock = new ReentrantLock();
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession httpSession;

    @Test
    public void testHttpSessionCartServiceGetCart() {
        Cart cart = new Cart();
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(HttpSessionCartService.class.getName() + ".cart")).thenReturn(null);
        CartService cartService = HttpSessionCartService.getInstance();
        assertNotNull(cartService);
        assertNotNull(cartService.getCart(request));
        when(httpSession.getAttribute(HttpSessionCartService.class.getName() + ".cart")).thenReturn(cart);
        assertEquals(cartService.getCart(request), cart);
    }

    @Test
    public void testHttpSessionCartServiceAdd() throws OutOfStockException {
        when(httpSession.getAttribute(HttpSessionCartService.class.getName() + ".lock")).thenReturn(lock);
        ProductDao productDao = ArrayListProductDao.getInstance();
        Currency usd = Currency.getInstance("USD");
        Product p = new Product("a", "a", new BigDecimal(1), usd, 100, "somelink");
        productDao.save(p);
        Cart cart = new Cart();
        CartService cartService = HttpSessionCartService.getInstance();
        assertNotNull(cartService);
        cartService.add(cart, 0L, 1, httpSession);
        assertEquals(cart.get(p), 1);
    }

    @Test(expected = OutOfStockException.class)
    public void testHttpSessionCartServiceAddException() throws OutOfStockException {
        when(httpSession.getAttribute(HttpSessionCartService.class.getName() + ".lock")).thenReturn(lock);
        ProductDao productDao = ArrayListProductDao.getInstance();
        Currency usd = Currency.getInstance("USD");
        Product p = new Product("a", "a", new BigDecimal(1), usd, 100, "somelink");
        productDao.save(p);
        Cart cart = new Cart();
        CartService cartService = HttpSessionCartService.getInstance();
        assertNotNull(cartService);
        cartService.add(cart, 0L, 1, httpSession);
        cartService.add(cart, 0L, 100, httpSession);
    }
}
