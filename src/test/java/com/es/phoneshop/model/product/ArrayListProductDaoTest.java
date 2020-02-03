package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testGetProductIsTrue(){
        assertTrue(productDao.getProduct(1L).equals(new Product(1L, "sgs",
                "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"))
        );
    }

    @Test
    public void saveProductToList(){
        Product product = new Product(Integer.toUnsignedLong(productDao.findProducts().size()+2), "sgs",
                "Samsung Galaxy Sss", new BigDecimal(100), Currency.getInstance("USD"), 100,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        productDao.save(product);
        assertTrue(productDao.getProduct(Integer.toUnsignedLong(productDao.findProducts().size()+1)).equals(product));
    }

    @Test
    public void deleteProductFromCenter() {

        Product product = new Product(Integer.toUnsignedLong(productDao.findProducts().size()), "simc61",
                "Siemens C61", new BigDecimal(80), Currency.getInstance("USD"), 30,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg");

        productDao.delete(Integer.toUnsignedLong(productDao.findProducts().size()+1));

        assertTrue(productDao.getProduct(Integer.toUnsignedLong(productDao.findProducts().size())).equals(product));
    }
}
