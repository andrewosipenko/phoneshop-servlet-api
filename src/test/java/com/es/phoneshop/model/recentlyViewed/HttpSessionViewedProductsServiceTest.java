package com.es.phoneshop.model.recentlyViewed;


import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static junit.framework.TestCase.*;


public class HttpSessionViewedProductsServiceTest {

    private ViewedProductsService viewedProductsService;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;

    @Before
    public void setUp() throws Exception {
        viewedProductsService = HttpSessionViewedProductsService.getInstance();
        request = Mockito.mock(HttpServletRequest.class);
        session = Mockito.mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("viewedProducts")).thenReturn(new RecentlyViewedProducts());
    }

    @Test
    public void testAddToViewedProducts() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg");

        viewedProductsService.add(viewedProductsService.getViewedProducts(request), product);
        List<Product> result = viewedProductsService.getViewedProducts(request).getViewedProducts();

        assertNotNull(result);
        assertEquals(result.get(0), product);
    }

    @Test
    public void testAddProductTwiceToViewedProducts() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg");

        viewedProductsService.add(viewedProductsService.getViewedProducts(request), product);
        viewedProductsService.add(viewedProductsService.getViewedProducts(request), product);
        List<Product> result = viewedProductsService.getViewedProducts(request).getViewedProducts();

        assertNotNull(result);
        assertTrue(result.size() == 1);
        assertEquals(result.get(0), product);
    }

    @Test
    public void testAdd4ProductsToViewedProducts() {
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg");
        Product product2 = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product3 = new Product(14L, "sgs1", "Samsung Galaxy S1", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product4 = new Product(18L, "sg", "Samsung Galaxy", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        RecentlyViewedProducts recentlyViewedProducts = viewedProductsService.getViewedProducts(request);
        viewedProductsService.add(recentlyViewedProducts, product1);
        viewedProductsService.add(recentlyViewedProducts, product2);
        viewedProductsService.add(recentlyViewedProducts, product3);
        viewedProductsService.add(recentlyViewedProducts, product4);
        List<Product> result = viewedProductsService.getViewedProducts(request).getViewedProducts();

        assertNotNull(result);
        assertTrue(result.size() == 3);
        assertEquals(result.get(0), product2);
        assertEquals(result.get(1), product3);
        assertEquals(result.get(2), product4);
    }

    @Test
    public void testEmpty() {
        List<Product> result = viewedProductsService.getViewedProducts(request).getViewedProducts();
        assertNotNull(result);
        assertTrue(result.size() == 0);
    }
}
