import model.ArrayListProductDAO;
import model.Product;
import model.ProductDAO;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.Assert.*;

public class ArrayListProductDAOTest {

    ProductDAO products;

    @Before
    public void init(){
        products = ArrayListProductDAO.getInstance();
        products.save(new Product(1L, "code", "desc", new BigDecimal(100),
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
        assertEquals(1L, products.getProduct(1L).getId().longValue());
    }

    @Test
    public void findProducts() {
        assertTrue(!products.findProducts().isEmpty());
    }

    @Test
    public void save() {
        products.save(new Product(2L, "code 2", "desc 2", new BigDecimal(200),
                Currency.getInstance(Locale.UK), 2));
        assertNotNull(products.getProduct(2L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void remove() {
        products.save(new Product(3L, "code 3", "desc 3", new BigDecimal(300),
                Currency.getInstance(Locale.UK), 3));
        products.remove(3L);
        products.getProduct(3L);
    }
}