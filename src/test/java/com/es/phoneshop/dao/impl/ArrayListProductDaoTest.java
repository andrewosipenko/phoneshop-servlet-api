package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.enums.SortingField;
import com.es.phoneshop.enums.SortingType;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    @Mock
    private ProductDao productDao;
    private List<Product> products;
    private static final Long NOT_EXISTING_ID = 100L;
    private static final String SEARCHING_PHASE = "Samsung II";
    private static final String EMPTY_DESCRIPTION = "";
    private static final SortingField SORTING_FIELD = SortingField.DESCRIPTION;
    private static final SortingType SORTING_TYPE = SortingType.ASC;
    private static final Long PRODUCT_ID = 1L;

    @Before
    public void setup() {
        Currency usd = Currency.getInstance("USD");
        products = new ArrayList<>();
        products.add(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        products.add(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        products.add(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        products.add(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        products.add(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
    }

    @Test
    public void testFindProductsForPriceAndStock() {
        when(productDao.findProducts(anyString(), any(), any())).thenReturn(products);

        List<Product> returnedProducts = productDao.findProducts(SEARCHING_PHASE, SORTING_FIELD, SORTING_TYPE);

        assertTrue(returnedProducts.stream()
                .allMatch(product -> product.getPrice() != null && product.getStock() > 0));
    }

    @Test
    public void testFindProductsNoResults() {
        when(productDao.findProducts(anyString(), any(), any())).thenReturn(products);

        assertFalse(productDao.findProducts(EMPTY_DESCRIPTION, SORTING_FIELD, SORTING_TYPE).isEmpty());
    }

    @Test
    public void testGetProduct() {
        Product product = new Product();
        product.setId(PRODUCT_ID);
        when(productDao.getProduct(anyLong())).thenReturn(product);

        Product result = productDao.getProduct(PRODUCT_ID);

        Long expectedProductId = 1L;
        assertEquals(expectedProductId, result.getId());
    }

    @Test
    public void testGetProductNotNull() {
        Product product = new Product();
        product.setId(PRODUCT_ID);
        when(productDao.getProduct(anyLong())).thenReturn(product);

        Product result = productDao.getProduct(PRODUCT_ID);

        assertNotNull(result);
    }

    @Test
    public void testSave() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);

        verify(productDao).save(product);
    }

    @Test
    public void testDelete() {
        productDao.delete(PRODUCT_ID);

        verify(productDao).delete(PRODUCT_ID);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testExceptionForNotFoundProductById() {
        when(productDao.getProduct(anyLong())).thenThrow(new ProductNotFoundException());

        productDao.getProduct(NOT_EXISTING_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionForNullProductParameter() {
        doThrow(new IllegalArgumentException()).when(productDao).save(null);

        productDao.save(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionForNullIdParameter() {
        doThrow(new IllegalArgumentException()).when(productDao).getProduct(null);

        productDao.getProduct(null);
    }
}
