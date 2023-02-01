package com.es.phoneshop.model;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.HttpSessionRecentlyViewedProduct;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecentlyViewedProductsTest {
    private HttpSessionRecentlyViewedProduct httpSessionRecentlyViewedProduct;

    private ProductDao productDao;

    private List<Product> products;

    private Product product;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Before
    public void setup() {
        httpSessionRecentlyViewedProduct = HttpSessionRecentlyViewedProduct.getInstance();
        productDao = ArrayListProductDao.getInstance();
        products = new ArrayList<>(3);
        Currency currency = Currency.getInstance("USD");
        product = new Product(4L, "test", "HTC EVO Shift 4G", new BigDecimal(320), currency, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        products.add(product);
        productDao.save(product);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(products);
    }

    @Test
    public void testAddViewedProduct() {
        httpSessionRecentlyViewedProduct.addViewedProduct(request, 4L);
        assertEquals(1, products.size());
    }

    @Test
    public void testAddMoreThenThree() {
        Currency currency = Currency.getInstance("USD");
        products.add(new Product(2L, "test", "HTC EVO Shift 4G", new BigDecimal(320), currency, 3, "https://raw.g"));
        products.add(new Product(3L, "test", "HTC EVO Shift 4G", new BigDecimal(320), currency, 3, "https://raw.g"));

        httpSessionRecentlyViewedProduct.addViewedProduct(request, 4L);

        assertEquals(3, products.size());
    }


}
