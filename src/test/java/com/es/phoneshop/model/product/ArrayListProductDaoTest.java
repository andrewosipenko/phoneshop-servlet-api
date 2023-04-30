package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;
    private Product productToSave;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
        Currency usd = Currency.getInstance("USD");
        productToSave = new Product("test", "test", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg");
    }

    @Test
    public void atTheBeginningArrayIsNotEmpty(){
        assertNotNull(productDao.findProducts());
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void getProduct(){
        Long id = (long) (productDao.findProducts().size() - 1);
        assertNotNull(productDao.getProduct(id));
    }

    @Test
    public void findProducts(){
        for(Product product: productDao.findProducts()){
            assertNotNull(product);
            assertNotNull(product.getPrice());
            assertTrue(product.getStock()>0);
        }
    }

    @Test
    public void save(){
        productDao.save(productToSave);
        Product result = productDao.getProduct(productToSave.getId());
        assertEquals(result, productToSave);
    }

    @Test
    public void delete(){
        productDao.delete(1L);
        assertThrows(RuntimeException.class,()-> productDao.getProduct(1L));
    }
}
