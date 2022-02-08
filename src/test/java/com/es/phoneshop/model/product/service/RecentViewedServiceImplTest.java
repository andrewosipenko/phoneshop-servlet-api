package com.es.phoneshop.model.product.service;

import com.es.phoneshop.model.product.bean.PriceHistory;
import com.es.phoneshop.model.product.bean.Product;
import com.es.phoneshop.model.product.bean.RecentViewCart;
import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.ProductDao;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecentViewedServiceImplTest extends TestCase {
    private RecentViewCart recentView;
    private RecentViewedService recentViewedService = RecentViewedServiceImpl.getInstance();
    private Product product;
    private ProductDao arrayListProductDao;
    @Mock
    private HttpSession httpSession;

    @Before
    public void setup() {
        recentView = new RecentViewCart();
        Currency usd = Currency.getInstance("USD");
        product = new Product("sgs", "Samsung Galaxy S", new PriceHistory(LocalDate.now(),
                new BigDecimal(100)), usd, 100,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/" +
                        "Samsung/Samsung%20Galaxy%20S.jpg");
        arrayListProductDao = ArrayListProductDao.getInstance();
        arrayListProductDao.save(product);
    }

    @Test
    public void givenHttpSession_WhenGetRecentViewed_ThenReturnNewRecentViewedCart() {
        when(httpSession.getAttribute(anyString())).thenReturn(null);

        RecentViewCart result = recentViewedService.getRecentViewed(httpSession);

        verify(httpSession).getAttribute(anyString());
        verify(httpSession).setAttribute(anyString(), any());
        assertNotNull(result);
        assertNotEquals(recentView, result);
    }

    @Test
    public void givenHttpSessionWithOldCart_WhenGetCart_ThenReturnOldCart() {
        when(httpSession.getAttribute(anyString())).thenReturn(recentView);

        RecentViewCart result = recentViewedService.getRecentViewed(httpSession);

        verify(httpSession).getAttribute(anyString());
        assertNotNull(result);
        assertEquals(recentView, result);
    }
}