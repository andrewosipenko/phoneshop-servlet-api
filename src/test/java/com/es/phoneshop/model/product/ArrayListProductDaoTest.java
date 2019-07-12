package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;
import static junit.framework.TestCase.assertEquals;



public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    private Product product;
    private Product productSave;
    private Currency usd ;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        usd = Currency.getInstance("USD");
        product = new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg");
        productSave = new Product(14L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

    }

    @Test
    public void testFindProducts() {
        int sizeBefore = productDao.findProducts().size();
        Product product = new Product(2L, "sgs3", "Samsung Galaxy S III", null, usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg");
        Product product1 = new Product(null, "sgs3", "Samsung Galaxy S III", new BigDecimal(100), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg");
        productDao.save(product);
        productDao.save(product1);
        assertEquals(sizeBefore + 1,productDao.findProducts().size());
    }

    @Test
    public void  testFindProductById(){
        Long id = 3L;
        assertEquals(product,productDao.getProduct(id));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindProductByIdException(){
        Long id = -1L;
        productDao.getProduct(id);
    }


    @Test
    public void testDeleteProduct(){
        int sizeBefore = productDao.findProducts().size();
        Long id = 3L;
        productDao.delete(id);
        assertEquals(sizeBefore - 1,productDao.findProducts().size());

    }

    @Test
    public void testSaveProduct(){
        int sizeBefore = productDao.findProducts().size();
        productDao.save(productSave);
        assertEquals(sizeBefore + 1,productDao.findProducts().size());

    }



}
