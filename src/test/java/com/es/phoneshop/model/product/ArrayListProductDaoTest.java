package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testGetProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product expectedProduct = new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg");
        Product actualProduct = productDao.getProduct(3L);
        assertEquals(expectedProduct, actualProduct);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testGetProductException() throws ProductNotFoundException {
        Product actualProduct = productDao.getProduct(14L);
    }

    @Test
    public void testFindProductsWithNotNullPriceAndStockMoreThan0()
    {
        List<Product> products = productDao.findProducts();
        int amountFalse = 0;
        for (Product product: products) {
            if (product.getPrice() == null || product.getStock() <= 0)
                amountFalse++;
        }
        assertTrue(amountFalse == 0);
    }

    @Test
    public void testSaveNewOrExistingProduct() throws ProductNotFoundException {
        Currency usd = Currency.getInstance("USD");
        Product productToSave = new Product( "sgs_new", "New Product Test", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg");
        productDao.save(productToSave);
        assertTrue(productToSave.getId() > 0);
        Product actualProduct = productDao.getProduct(productToSave.getId());
        assertEquals(productToSave, actualProduct);

        productToSave = new Product(3L, "sgs_updated", "Updated Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg");
        productDao.save(productToSave);
        actualProduct = productDao.getProduct(productToSave.getId());
        assertEquals(productToSave, actualProduct);
    }

    @Test
    public void testDeleteProduct() throws ProductNotFoundException {
        long idToDelete = 13L;
        productDao.delete(idToDelete);
        boolean isDeleted = false;
        try {
            Product product = productDao.getProduct(idToDelete);
        }
        catch (ProductNotFoundException e)
        {
            isDeleted = true;
        }
        assertTrue(isDeleted);
    }

}
