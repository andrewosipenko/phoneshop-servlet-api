package com.es.phoneshop.recentViewed;

import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RecentViewedServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession httpSession;

    private RecentViewed recentViewed;

    @Before
    public void setup() {
        recentViewed = RecentViewedService.getInstance();
        when(request.getSession()).thenReturn(httpSession);
    }

    @Test
    public void addTest() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        product.setId((long) 0);
        recentViewed.addToRecentViewed(request, product);
        verify(request).setAttribute(eq("product"),any(Product.class));
        verify(request).setAttribute(eq("cart"), any(Cart.class));
        verify(request).setAttribute(eq("recentViewedList"),anyCollection());
    }
}


