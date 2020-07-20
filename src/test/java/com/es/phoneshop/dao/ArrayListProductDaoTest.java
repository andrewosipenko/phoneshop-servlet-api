package com.es.phoneshop.dao;

import com.es.phoneshop.model.SortField;
import com.es.phoneshop.model.SortOrder;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductDaoTest {
    @InjectMocks
    private final ArrayListProductDao productDao = ArrayListProductDao.getInstance();

    private List<Product> testProducts;

    @Mock
    private Product product1;

    @Mock
    private Product product2;

    @Mock
    private Product product3;

    @Mock
    private Product product4;

    @Mock
    private Product product5;

    @Mock
    private Product product6;

    @Mock
    private Product productToSave;

    @Before
    public void setup() {
        testProducts = new ArrayList<>();
        when(product1.getId()).thenReturn(15L);
        when(product1.getPrice()).thenReturn(null);
        testProducts.add(product1);

        when(product2.getPrice()).thenReturn(new BigDecimal(1000));
        when(product2.getStock()).thenReturn(30);
        when(product2.getDescription()).thenReturn("Apple iPhone 6");
        testProducts.add(product2);

        testProducts.add(product3);

        when(productToSave.getId()).thenReturn(null);

        when(product4.getPrice()).thenReturn(new BigDecimal(100));
        when(product4.getStock()).thenReturn(30);
        when(product5.getPrice()).thenReturn(new BigDecimal(300));
        when(product5.getStock()).thenReturn(30);
        when(product6.getPrice()).thenReturn(new BigDecimal(320));
        when(product6.getStock()).thenReturn(30);
        when(product4.getDescription()).thenReturn("Samsung Galaxy S");
        when(product5.getDescription()).thenReturn("Samsung Galaxy S III");
        when(product6.getDescription()).thenReturn("HTC EVO Shift 4G");
        testProducts.add(product4);
        testProducts.add(product5);
        testProducts.add(product6);

        productDao.setProductList(testProducts);

    }

    @Test
    public void testGetProduct() {
        Product actualProduct = productDao.getProduct(15L);
        assertEquals(product1, actualProduct);

    }

    @Test(expected = RuntimeException.class)
    public void testGetProductException() {
        productDao.getProduct(14L);

    }

    @Test(expected = RuntimeException.class)
    public void testIfProductsWithNullPriceOrZeroStockFound() {
        productDao.findProducts("", null, null).stream()
                .filter(product -> product.getPrice() == null || product.getStock() <= 0)
                .findAny()
                .orElseThrow(RuntimeException::new);

    }

    @Test
    public void testFindProductsByQuery() {
        List<Product> resultList = new ArrayList<>(Arrays.asList(product5, product4));
        assertEquals(resultList, productDao.findProducts("Samsung III", null, null));

    }

    @Test
    public void testFindProductsDescriptionSort() {
        List<Product> resultList1 = new ArrayList<>(Arrays.asList(product2, product6, product4, product5));
        List<Product> resultList2 = new ArrayList<>(Arrays.asList(product5, product4, product6, product2));
        List<Product> resultList3 = new ArrayList<>(Arrays.asList(product4, product5));
        List<Product> resultList4 = new ArrayList<>(Arrays.asList(product5, product4));
        assertEquals(resultList1, productDao.findProducts("", SortField.description, SortOrder.asc));
        assertEquals(resultList2, productDao.findProducts("", SortField.description, SortOrder.desc));
        assertEquals(resultList3, productDao.findProducts("Samsung III", SortField.description, SortOrder.asc));
        assertEquals(resultList4, productDao.findProducts("Samsung III", SortField.description, SortOrder.desc));

    }

    @Test
    public void testFindProductsPriceSort() {
        List<Product> resultList1 = new ArrayList<>(Arrays.asList(product4, product5, product6, product2));
        List<Product> resultList2 = new ArrayList<>(Arrays.asList(product2, product6, product5, product4));
        List<Product> resultList3 = new ArrayList<>(Arrays.asList(product4, product5, product6));
        List<Product> resultList4 = new ArrayList<>(Arrays.asList(product6, product5, product4));
        assertEquals(resultList1, productDao.findProducts("", SortField.price, SortOrder.asc));
        assertEquals(resultList2, productDao.findProducts("", SortField.price, SortOrder.desc));
        assertEquals(resultList3, productDao.findProducts("S", SortField.price, SortOrder.asc));
        assertEquals(resultList4, productDao.findProducts("S", SortField.price, SortOrder.desc));

    }

    @Test
    public void testSaveNewProduct() {
        productDao.save(productToSave);
        verify(productToSave).setId(anyLong());
        assertTrue(testProducts.contains(productToSave));

    }

    @Test
    public void testSaveExistingProduct() {
        productDao.save(product1);
        verify(product1, never()).setId(anyLong());
        assertTrue(testProducts.contains(product1));

    }

    @Test
    public void testDeleteProduct() {
        long idToDelete = 15L;
        productDao.delete(idToDelete);
        assertTrue(!testProducts.contains(product1));

    }

}
