package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.ProductNotFoundException;
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
    @Mock
    Product product2;

    @Before
    public void setup() {
        ((ArrayListProductDao) productListService.getProductDao()).getProductList().clear();

        when(product.getId()).thenReturn(0L);
        when(product1.getId()).thenReturn(1L);
        when(product2.getId()).thenReturn(2L);

        productListService.save(product);
        productListService.save(product1);
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
    public void testSearchWithEmptyQuery() {
        assertEquals(productListService.findProducts("", null, null),
                productListService.getProductDao().findProducts());
    }

    @Test
    public void testFindProductsWithoutQFO() {
        assertEquals(productListService.findProducts(null, null, null),
                productListService.getProductDao().findProducts());
    }

    @Test
    public void testFindProductsWithoutFO() {
        String query = "Apple";
        assertEquals(productListService.findProducts(query, null, null),
                productListService.search(query));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveBadProduct() {
        productListService.save(product);
    }

    @Test
    public void testSave() {
        int oldSize = ((ArrayListProductDao) productListService.getProductDao()).getProductList().size();

        productListService.save(product2);

        assertEquals(((ArrayListProductDao) productListService.getProductDao()).getProductList().size(), oldSize + 1);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteNonexistentProduct() {
        productListService.delete(-1L);
    }

    @Test
    public void testDelete() {
        int oldSize = ((ArrayListProductDao) productListService.getProductDao()).getProductList().size();

        productListService.delete(product.getId());

        assertEquals(((ArrayListProductDao) productListService.getProductDao()).getProductList().size(), oldSize - 1);
    }
}
