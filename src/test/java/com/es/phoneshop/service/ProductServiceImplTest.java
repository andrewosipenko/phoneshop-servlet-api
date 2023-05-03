package com.es.phoneshop.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Mock;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductServiceImplTest {

    private ProductServiceImpl productService;

    @Mock
    private ProductDao productDaoMock;

    @Before
    public void setup() {
        productService = ProductServiceImpl.getInstance();
        productDaoMock = Mockito.mock(ProductDao.class);
        productService.setProductDao(productDaoMock);
    }

    @Test
    public void givenValidProduct_whenGetProduct_thenGetProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setCode("samsung-galaxy-s20");
        product.setDescription("Samsung Galaxy S20");
        product.setPrice(new BigDecimal(2));
        product.setStock(10);

        Mockito.when(productDaoMock.getProduct(1L)).thenReturn(Optional.of(product));
        Optional<Product> result = productService.getProduct(1L);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }

    @Test
    public void givenValidProductArray_whenFindProduct_thenGetProductArray() {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1L);
        product1.setCode("samsung-galaxy-s20");
        product1.setDescription("Samsung Galaxy S20");
        product1.setPrice(new BigDecimal(2));
        product1.setStock(10);
        productList.add(product1);
        Product product2 = new Product();
        product2.setId(2L);
        product2.setCode("iphone-12-pro");
        product2.setDescription("iPhone 12 Pro");
        product2.setPrice(new BigDecimal(2));
        product2.setStock(5);
        productList.add(product2);

        Mockito.when(productDaoMock.findProducts()).thenReturn(productList);
        List<Product> result = productService.findProducts();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(productList.size(), result.size());
        assertEquals(productList, result);
    }

    @Test
    public void givenValidProduct_whenSaveProduct_thenReturnThisProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setCode("samsung-galaxy-s20");
        product.setDescription("Samsung Galaxy S20");
        product.setPrice(new BigDecimal(2));
        product.setStock(10);

        productService.save(product);

        Mockito.verify(productDaoMock, Mockito.times(1)).save(product);
    }

    @Test(expected = ProductNotFoundException.class)
    public void givenInvalidProductId_whenDeleteProduct_thenThrowProductNotFoundException() throws ProductNotFoundException {
        Mockito.doThrow(ProductNotFoundException.class).when(productDaoMock).delete(1L);

        productService.delete(1L);
    }

    @Test
    public void givenValidProductId_whenDeleteProduct_thenDeleteThisProduct() throws ProductNotFoundException {
        productService.delete(1L);

        Mockito.verify(productDaoMock, Mockito.times(1)).delete(1L);
    }

}
