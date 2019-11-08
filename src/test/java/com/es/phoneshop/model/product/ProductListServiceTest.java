package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductListServiceTest {
    private ProductListService productListService = new ProductListService();

    @Mock
    Product product;
    @Mock
    Product product1;
    @Mock
    Product product2;
    @Mock
    Product product3;

    @Before
    public void setup() {
        ((ArrayListProductDao)productListService.getProductDao()).getProductList().clear();

        when(product.getId()).thenReturn(0L);
        when(product1.getId()).thenReturn(1L);
        when(product2.getId()).thenReturn(2L);
        when(product3.getId()).thenReturn(3L);

        productListService.save(product);
        productListService.save(product1);
        productListService.save(product2);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductBadId() {
        productListService.getProduct(-1L);
    }

    @Test
    public void testGetProduct() {
        assertEquals(product, productListService.getProduct(product.getId()));
    }

    @Test
    public void testFindProductsWithoutQFO() {
        BigDecimal price = new BigDecimal(1);
        when(product.getPrice()).thenReturn(price);
        when(product1.getPrice()).thenReturn(price);
        when(product2.getPrice()).thenReturn(null);

        when(product.getStock()).thenReturn(1);
        when(product1.getStock()).thenReturn(0);
        when(product2.getStock()).thenReturn(1);

        assertEquals(productListService.findProducts(null, null, null),
                productListService.getProductDao().findProducts());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveBadProduct() {
        productListService.save(product);
    }

    @Test
    public void testSave() {
        int oldSize = ((ArrayListProductDao)productListService.getProductDao()).getProductList().size();

        productListService.save(product3);

        assertEquals(((ArrayListProductDao)productListService.getProductDao()).getProductList().size(), oldSize + 1);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteNonexistentProduct() {
        productListService.delete(-1L);
    }

    @Test
    public void testDelete() {
        int oldSize = ((ArrayListProductDao)productListService.getProductDao()).getProductList().size();

        productListService.delete(product.getId());

        assertEquals(((ArrayListProductDao)productListService.getProductDao()).getProductList().size(), oldSize - 1);
    }
}
