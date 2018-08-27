import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ProductDao;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao = ArrayListProductDao.getINSTANCE();
    private final Long id = 1L, id2 = 2L, id3 = 3L;
    private final Product product = new Product(id, "A1B", "desc1", new BigDecimal("123.3"),
            Currency.getInstance(Locale.UK), 1);
    private final Product productWithNullPrice = new Product(id2, "A1B", "desc1",
            new BigDecimal(0), Currency.getInstance(Locale.UK), 1);
    private final Product productWithNullStock = new Product(id3, "A1B", "desc1",
            new BigDecimal(12), Currency.getInstance(Locale.UK), 0);


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void deleteProduct(){
      if(!productDao.findProducts().isEmpty()) {
          if(productDao.findProducts().size()>1){
              productDao.remove(id2);
              productDao.remove(id3);
          }
          productDao.remove(id);
      }
    }

    @Test
    public void findProducts() {
        productDao.save(product);
        productDao.save(productWithNullPrice);
        productDao.save(productWithNullStock);

        assertEquals(1, productDao.findProducts().size());
    }

    @Test
    public void getProduct() {
        productDao.save(product);
        assertEquals(product, productDao.getProduct(id));
    }

    public void remove() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("There is no products with such id: " + id);

        productDao.save(product);
        productDao.remove(id);
        productDao.getProduct(id);
    }

    @Test
    public void noSuchProduct(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("There is no products with such id: " + id);

        productDao.getProduct(id);
    }

    @Test
    public void getInstance() {
        assertNotNull(ArrayListProductDao.getINSTANCE());
    }

    @Test
    public void checkEqualsOfInstances(){
        assertEquals(ArrayListProductDao.getINSTANCE(), ArrayListProductDao.getINSTANCE());
    }
}