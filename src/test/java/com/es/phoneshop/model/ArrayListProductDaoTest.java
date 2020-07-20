package com.es.phoneshop.model;

import com.es.phoneshop.dao.ArrayListProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {

    @Mock
    private Product product1;
    @Mock
    private Price price1;
    @Mock
    private Product product2;
    @Mock
    private Price price2;

    @Mock
    private Product productForSave;

    @Mock
    private Price priceForSave;

    @Spy
    private ArrayList<Product> products;

    @InjectMocks
    private ArrayListProductDao arrayListProductDao = ArrayListProductDao.getInstance();

    public static final long NEW_ID_FOR_PRODUCT = 2L;
    public static final long ID_FIRST_PRODUCT = 0L;

    @Before
    public void setup() {
        products.addAll(Arrays.asList(product1, product2));
        when(product1.getStock()).thenReturn(42);
        when(product1.getDescription()).thenReturn("Iphone");
        when(product1.getId()).thenReturn(ID_FIRST_PRODUCT);
        when(product1.getCurrentPrice()).thenReturn(price1);
        when(price1.getCost()).thenReturn(new BigDecimal(200));

        when(product2.getStock()).thenReturn(30);
        when(product2.getDescription()).thenReturn("Nokia");
        when(product2.getId()).thenReturn(1L);
        when(product2.getCurrentPrice()).thenReturn(price2);
        when(price2.getCost()).thenReturn(new BigDecimal(25000));

        when(productForSave.getStock()).thenReturn(30);
        when(productForSave.getDescription()).thenReturn("Xiaomi");
        when(productForSave.getCurrentPrice()).thenReturn(price2);
        when(priceForSave.getCost()).thenReturn(new BigDecimal(2500));


    }

    @Test
    public void testFindProductsWithoutAllArguments() {
        List<Product> getProducts = arrayListProductDao.findProducts(null, null, null);
        assertEquals(getProducts.size(), 2);
    }

    @Test
    public void testFindProductOnlyWithQuery() {
        List<Product> foundProducts = arrayListProductDao.findProducts("Iphone", null, null);
        assertEquals(foundProducts.size(), 1);
    }

    @Test
    public void testFindProductWithoutQueryByDescDescription() {
        List<Product> foundProducts = arrayListProductDao.findProducts(null, "description", "desc");
        assertTrue(foundProducts.get(0).getDescription().compareTo(foundProducts.get(1).getDescription()) > 0);
    }


    @Test
    public void testFindProductWithoutQueryByAscDescription() {
        List<Product> foundProducts = arrayListProductDao.findProducts(null, "description", "asc");
        assertTrue(foundProducts.get(0).getDescription().compareTo(foundProducts.get(1).getDescription()) < 0);
    }

    @Test
    public void testFindProductsWithoutQueryByAscPrice() {
        List<Product> foundProducts = arrayListProductDao.findProducts(null, "price", "asc");
        assertTrue(foundProducts.get(0).getCurrentPrice().getCost().intValue() <
                foundProducts.get(1).getCurrentPrice().getCost().intValue());
    }

    @Test
    public void testFindProductsWithoutQueryByDescPrice() {
        List<Product> foundProducts = arrayListProductDao.findProducts(null, "price", "desc");
        assertTrue(foundProducts.get(0).getCurrentPrice().getCost().intValue() >
                foundProducts.get(1).getCurrentPrice().getCost().intValue());
    }

    @Test
    public void saveNewProduct() {
        when(productForSave.getId()).thenReturn(null);
        arrayListProductDao.save(productForSave);
        verify(productForSave).setId(NEW_ID_FOR_PRODUCT);
    }

    @Test
    public void updateProduct() {
        when(productForSave.getId()).thenReturn(ID_FIRST_PRODUCT);
        arrayListProductDao.save(productForSave);
        verify(product1).setDescription(any());
    }
}

