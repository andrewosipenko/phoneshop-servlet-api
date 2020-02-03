package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    Currency usd = Currency.getInstance("USD");

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void getProduct() {
        Long id = 1L;
        productDao.save(new Product(id, "1", "description 1",
                new BigDecimal(100), usd, 100, "https://www.notebookcheck-ru.com/typo3temp/_processed_/1/6/csm_4_zu_3_Moto_G8_Plus_aed36866ca.jpg"));
        Product product = productDao.getProduct(id);
        assertEquals(id, product.getId());
    }

    @Test
    public void findProducts(){
        Long id = 2L;
        productDao.save(new Product(id, "2", "description 2",
                new BigDecimal(400), usd, 10, "https://img.alicdn.com/imgextra/i3/2404040152/O1CN01E8FCgB1CzehJlqOHb_!!2404040152.jpg" ));
        List<Product> productList = productDao.findProducts();
        assertFalse(productList.isEmpty());
    }

    @Test
    public void save() {
        Long id = 3L;
        productDao.save(new Product(id, "3", "description 3",
                new BigDecimal(100), usd, 300, "https://img.alicdn.com/imgextra/i3/2404040152/O1CN01E8FCgB1CzehJlqOHb_!!2404040152.jpg"));
        Product product = productDao.getProduct(id);
        assertNotNull(product);
    }

    @Test
    public void delete() {
        Long id = 3L;
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.delete(id);
        Product product = productDao.getProduct(id);
        assertNull(product);
    }
}
