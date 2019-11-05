package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductListServiceTest {
    private ProductListService productListService = new ProductListService();

    @Mock
    Product product;
    @Mock
    Product product1;

    @Before
    public void setup() {
        ((ArrayListProductDao)productListService.getProductDao()).getProductList().clear();

        productListService.save(product);
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
        assertEquals(productListService.findProducts(null, null, null),
                productListService.getProductDao().findProducts());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveBadProduct() {
        when(product.getId()).thenReturn(1L);

        productListService.save(product);
    }

    @Test
    public void testSave() {
        int oldSize = ((ArrayListProductDao)productListService.getProductDao()).getProductList().size();
        when(product.getId()).thenReturn(1L);
        when(product1.getId()).thenReturn(2L);

        productListService.save(product1);

        assertEquals(((ArrayListProductDao)productListService.getProductDao()).getProductList().size(), oldSize + 1);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteNonexistentProduct() {
        productListService.delete(-1L);
    }

    @Test
    public void testDelete() {
        when(product.getId()).thenReturn(1L);
        int oldSize = ((ArrayListProductDao)productListService.getProductDao()).getProductList().size();

        productListService.delete(product.getId());

        assertEquals(((ArrayListProductDao)productListService.getProductDao()).getProductList().size(), oldSize - 1);
    }
}
