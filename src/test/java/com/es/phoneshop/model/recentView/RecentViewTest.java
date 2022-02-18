package com.es.phoneshop.model.recentView;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
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
public class RecentViewTest {
    private final Lock lock = new ReentrantLock();

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession httpSession;

    @Test
    public void testRecentView() {
        Currency usd = Currency.getInstance("USD");
        Product p1 = new Product("a", "a", new BigDecimal(1), usd, 100, "somelink");
        Product p2 = new Product("b", "b", new BigDecimal(1), usd, 100, "somelink");
        Product p3 = new Product("c", "c", new BigDecimal(1), usd, 100, "somelink");
        Product p4 = new Product("d", "d", new BigDecimal(1), usd, 100, "somelink");
        ProductDao productDao = ArrayListProductDao.getInstance();
        productDao.save(p1);
        productDao.save(p2);
        productDao.save(p3);
        productDao.save(p4);
        RecentView recentView = new RecentView(lock);
        recentView.add(p1);
        recentView.add(p2);
        recentView.add(p3);
        assertTrue(recentView.getDeque().contains(p1));
        assertTrue(recentView.getDeque().contains(p2));
        assertTrue(recentView.getDeque().contains(p3));
        assertFalse(recentView.getDeque().contains(p4));
        recentView.add(p4);
        assertFalse(recentView.getDeque().contains(p1));
        assertTrue(recentView.getDeque().contains(p2));
        assertTrue(recentView.getDeque().contains(p3));
        assertTrue(recentView.getDeque().contains(p4));
        recentView.add(p2);
        assertFalse(recentView.getDeque().contains(p1));
        assertTrue(recentView.getDeque().contains(p2));
        assertTrue(recentView.getDeque().contains(p3));
        assertTrue(recentView.getDeque().contains(p4));

        assertEquals(recentView.getDeque().pop(), p2);
        assertEquals(recentView.getDeque().pop(), p4);
        assertEquals(recentView.getDeque().pop(), p3);
    }

    @Test
    public void testRecentViewService() {
        RecentView recentView = new RecentView(lock);
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(anyString())).thenReturn(null);
        RecentViewService recentViewService = HttpSessionRecentViewService.getInstance();
        assertNotNull(recentViewService);
        assertNotNull(recentViewService.getRecentView(request));
        when(httpSession.getAttribute(HttpSessionRecentViewService.class.getName() + ".recentView")).thenReturn(recentView);
        assertEquals(recentViewService.getRecentView(request), recentView);
    }
}
