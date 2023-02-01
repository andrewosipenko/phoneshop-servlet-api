package com.es.phoneshop.model.recentlyViewedProducts;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.service.DefaultRecentlyViewedProductsService;
import com.es.phoneshop.service.RecentlyViewedProductsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Deque;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRecentlyViewedProductsServiceTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private ProductDao productDao;
    private RecentlyViewedProductsService recentlyViewedProductsService;

    @Before
    public void setup() {
        this.productDao = ArrayListProductDao.getInstance();
        Currency usd = Currency.getInstance("USD");

        Product product1 = new Product("test1", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product1);
        Product product2 = new Product("test2", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product2);
        Product product3 = new Product("test3", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product3);
        Product product4 = new Product("test4", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product4);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(DefaultRecentlyViewedProductsService.class.getName() + ".recently_viewed_products")).thenReturn(new RecentlyViewedProducts());

        this.recentlyViewedProductsService = DefaultRecentlyViewedProductsService.getInstance();

        recentlyViewedProductsService.add(1L, request);
        recentlyViewedProductsService.add(2L, request);
        recentlyViewedProductsService.add(3L, request);
    }

    @Test
    public void testAddExistedProduct() {
        Product product = productDao.getProduct(1L);

        Deque<Product> recentlyViewedProductsDeque = recentlyViewedProductsService.getProducts(request).getProducts();
        recentlyViewedProductsService.add(1L, request);

        assertEquals(product, recentlyViewedProductsDeque.peek());
    }

    @Test
    public void testAddFourthProduct() {
        Product fourthProduct = productDao.getProduct(4L);

        Deque<Product> recentlyViewedProductsDeque = recentlyViewedProductsService.getProducts(request).getProducts();
        recentlyViewedProductsService.add(4L, request);

        assertEquals(fourthProduct, recentlyViewedProductsDeque.peek());
        assertEquals(3, recentlyViewedProductsDeque.size());
    }
}
