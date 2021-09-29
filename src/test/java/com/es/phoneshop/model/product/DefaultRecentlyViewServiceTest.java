package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.productdao.Product;
import com.es.phoneshop.model.product.recentlyview.DefaultRecentlyViewService;
import com.es.phoneshop.model.product.recentlyview.RecentlyViewSection;
import com.es.phoneshop.model.product.recentlyview.RecentlyViewService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class DefaultRecentlyViewServiceTest {
    private RecentlyViewSection recentlyViewSection;
    private RecentlyViewService recentlyViewService;
    public static final String RECENTLY_VIEW_SECTION_ATTRIBUTE = DefaultRecentlyViewService.class.getName() + ".recentlyViewSection";

    @Spy
    HttpSession session;
    @Spy
    HttpServletRequest request;

    @Before
    public void setup() throws IllegalAccessException, NoSuchFieldException {
        recentlyViewSection = new RecentlyViewSection();
        recentlyViewService = DefaultRecentlyViewService.getInstance();

        session = spy(HttpSession.class);
        when(session.getAttribute(RECENTLY_VIEW_SECTION_ATTRIBUTE)).thenReturn(recentlyViewSection);
        request = spy(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);

        resetSingletonRecentlyView();
        }

    public void resetSingletonRecentlyView() throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = DefaultRecentlyViewService.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void testSingleton(){
        RecentlyViewService recentlyViewService1 = DefaultRecentlyViewService.getInstance();
        RecentlyViewService recentlyViewService2 = DefaultRecentlyViewService.getInstance();
        assertEquals(recentlyViewService1, recentlyViewService2);
    }

    @Test
    public void testGetRecentlyViewSectionEmpty(){
        assertEquals(recentlyViewService.getRecentlyViewSection(request), new RecentlyViewSection());
    }

    @Test
    public void testGetRecentlyViewSection(){
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(0L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        recentlyViewSection.getRecentlyView().add(product);
        RecentlyViewSection recentlyViewSectionExpected = new RecentlyViewSection();
        recentlyViewSectionExpected.getRecentlyView().add(product);
        assertEquals(recentlyViewSectionExpected, recentlyViewService.getRecentlyViewSection(request));
    }
}
