package test.com.es.phoneshop.web;

import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ProductDao;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    ProductDao productDao = ArrayListProductDao.getInstance();

    @org.junit.Test
    public void findProducts() {
        productDao.save(new Product(1L, "A1B", "desc1", new BigDecimal("123.3"),
                Currency.getInstance(Locale.UK), 1));
        assertTrue(!productDao.findProducts().isEmpty());
    }

    @org.junit.Test
    public void getProduct() {
        productDao.save(new Product(12L, "A1B", "desc1", new BigDecimal("123.3"),
                Currency.getInstance(Locale.UK), 1));
        assertEquals(12L, productDao.getProduct(12L).getId().longValue());
    }

    @org.junit.Test
    public void save() {
        productDao.save(new Product(19L, "A1B", "desc1", new BigDecimal("123.3"),
                Currency.getInstance(Locale.UK), 1));
        assertEquals(19L, productDao.getProduct(19L).getId().longValue());
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void remove() {
        productDao.save(new Product(20L, "A1B", "desc1", new BigDecimal("123.3"),
            Currency.getInstance(Locale.UK), 1));
        productDao.remove(20L);
        productDao.getProduct(20L);
    }

    @org.junit.Test
    public void getInstance() {
        assertNotNull(ArrayListProductDao.getInstance());
    }
}