package com.es.phoneshop.service;

import com.es.phoneshop.model.product.Product;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RecentlyViewedProductsServiceImplTest {

    private RecentlyViewedProductsServiceImpl recentlyViewedProductsService;

    @Mock
    private HttpSession session;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recentlyViewedProductsService = RecentlyViewedProductsServiceImpl.getInstance();
    }

    @Test
    public void testAddRecentlyViewedProduct() {
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(2L);
        Product product3 = new Product();
        product3.setId(3L);

        List<Product> recentlyViewedProducts = mock(List.class);
        when(session.getAttribute("recentlyViewedProducts")).thenReturn(recentlyViewedProducts);

        recentlyViewedProductsService.addRecentlyViewedProduct(session, product1);
        recentlyViewedProductsService.addRecentlyViewedProduct(session, product2);
        recentlyViewedProductsService.addRecentlyViewedProduct(session, product3);

        verify(recentlyViewedProducts, times(3)).add(anyInt(), any(Product.class));
        verify(recentlyViewedProducts, times(1)).remove(product3);
    }

    @Test
    public void testGetRecentlyViewedProducts_emptyList() {
        List<Product> recentlyViewedProducts = recentlyViewedProductsService.getRecentlyViewedProducts(session);

        assertEquals(0, recentlyViewedProducts.size());

        verify(session, times(1)).getAttribute("recentlyViewedProducts");
        verify(session, times(1)).setAttribute(eq("recentlyViewedProducts"), any(List.class));
    }
}
