package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.BrowsingHistory;
import com.es.phoneshop.model.Product;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class BrowsingHistoryServiceImplTest {
    @InjectMocks
    private BrowsingHistoryServiceImpl browsingHistoryService;
    @Mock
    private ProductDao productDao;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    private BrowsingHistory browsingHistory;

    @Before
    public void setup() {
        browsingHistory = new BrowsingHistory();
        Currency usd = Currency.getInstance("USD");
        LinkedList<Product> products = new LinkedList<>();
        products.add(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        products.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        products.add(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        browsingHistory.setProducts(products);
    }

    @Test
    public void testGetBrowsingHistoryWhenHistoryNotExist() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(null);

        browsingHistoryService.getBrowsingHistory(request);

        verify(session).setAttribute(anyString(), any());
    }

    public void testGetBrowsingHistoryWhenHistoryExist() {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(browsingHistory);

        browsingHistoryService.getBrowsingHistory(request);

        verify(session, times(0)).setAttribute(anyString(), any());
    }

    @Test
    public void testAddNewProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
        when(productDao.getProduct(anyLong())).thenReturn(product);

        browsingHistoryService.add(4L, browsingHistory);

        Long expectedFirstProductId = 4L;
        assertEquals(expectedFirstProductId, browsingHistory.getProducts().get(0).getId());
    }

    @Test
    public void testAddExistingProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(2L,"sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        when(productDao.getProduct(anyLong())).thenReturn(product);

        browsingHistoryService.add(2L, browsingHistory);

        Long expectedFirstProductId = 2L;
        assertEquals(expectedFirstProductId, browsingHistory.getProducts().get(0).getId());
        Long expectedSecondProductId = 1L;
        assertEquals(expectedSecondProductId, browsingHistory.getProducts().get(1).getId());
    }
}
