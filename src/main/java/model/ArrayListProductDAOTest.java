package model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.Assert.*;

public class ArrayListProductDAOTest {

    ProductDAO productDAO;

    @Before
    public void init(){
        productDAO = ArrayListProductDAO.getInstance();
        productDAO.save(new Product(1L, "code", "desc", new BigDecimal(100),
                Currency.getInstance(Locale.UK), 10));
    }

    @Test
    public void generateId() {
        assertNotNull(ArrayListProductDAO.generateId());
    }

    @Test
    public void getInstance() {
        assertNotNull(ArrayListProductDAO.getInstance());
    }

    @Test
    public void getProduct() {
        assertEquals(1L, productDAO.getProduct(1L).getId().longValue());
    }

    @Test
    public void findProducts() {
        assertTrue(!productDAO.findProducts().isEmpty());
    }

    @Test
    public void save() {
        productDAO.save(new Product(2L, "code 2", "desc 2", new BigDecimal(200),
                Currency.getInstance(Locale.UK), 2));
        assertNotNull(productDAO.getProduct(2L));
    }

    @Test
    public void remove() {
        productDAO.remove(2L);
        assertNull(productDAO.getProduct(2L));
    }
}