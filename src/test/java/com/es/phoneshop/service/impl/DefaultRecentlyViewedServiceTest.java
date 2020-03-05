package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRecentlyViewedServiceTest {
    private static final String RECENTLY_VIEWED_ATTRIBUTE = "recentlyViewed_" + DefaultRecentlyViewedService.class;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @Mock
    private Product product4;
    @Spy
    private ArrayList<Product> recentlyViewed;
    @InjectMocks
    private DefaultRecentlyViewedService recentlyViewedService;

    @Before
    public void setup() {
        recentlyViewedService = DefaultRecentlyViewedService.getInstance();

        when(request.getSession()).thenReturn(session);
        recentlyViewed.add(product1);
    }

    @Test
    public void testGetProducts() {
        when(session.getAttribute(RECENTLY_VIEWED_ATTRIBUTE)).thenReturn(null);

        recentlyViewedService.getProducts(request);

        verify(session).setAttribute(eq(RECENTLY_VIEWED_ATTRIBUTE), any());
    }

    @Test
    public void testAddProduct() {
        when(session.getAttribute(RECENTLY_VIEWED_ATTRIBUTE)).thenReturn(recentlyViewed);

        recentlyViewedService.addProduct(request, product2);

        assertEquals(Arrays.asList(product2, product1), recentlyViewed);
    }

    @Test
    public void testAddProductWithEqualId() {
        when(session.getAttribute(RECENTLY_VIEWED_ATTRIBUTE)).thenReturn(recentlyViewed);

        recentlyViewedService.addProduct(request, product1);

        assertEquals(Collections.singletonList(product1), recentlyViewed);
    }

    @Test
    public void testAddProductToFullList() {
        when(session.getAttribute(RECENTLY_VIEWED_ATTRIBUTE)).thenReturn(recentlyViewed);

        recentlyViewedService.addProduct(request, product2);
        recentlyViewedService.addProduct(request, product3);
        recentlyViewedService.addProduct(request, product4);

        assertEquals(Arrays.asList(product4, product3, product2), recentlyViewed);
    }
}
