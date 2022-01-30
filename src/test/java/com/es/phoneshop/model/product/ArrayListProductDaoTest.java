package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Test
    public void testDeleteProduct() {
        Currency usd = Currency.getInstance("USD");
        Product p = new Product("sgs", "Xiaomi", new BigDecimal(100), usd, 100, "https://just/a/shortened/link.jpg");
        productDao.save(p);
        assertTrue(productDao.getProduct(p.getId()).isPresent());
        productDao.delete(p.getId());
        assertFalse(productDao.getProduct(p.getId()).isPresent());
    }

    @Test
    public void testFindProducts() {
        assertFalse(productDao.findProducts(null, null, null).isEmpty());
        Currency usd = Currency.getInstance("USD");
        Product p = new Product("sgs", "Xiaomi", new BigDecimal(100), usd, 100, "https://just/a/shortened/link.jpg");
        productDao.save(p);
        assertTrue(productDao.findProducts(null, null, null).contains(p));
        p = new Product("sgs", "Xiaomi", new BigDecimal(100), usd, 0, "https://just/a/shortened/link.jpg");
        productDao.save(p);
        assertFalse(productDao.findProducts(null, null, null).contains(p));
        p = new Product("sgs", "Xiaomi", null, usd, 10, "https://just/a/shortened/link.jpg");
        productDao.save(p);
        assertFalse(productDao.findProducts(null, null, null).contains(p));
    }

    @Test
    public void testSaveProduct() {
        Currency usd = Currency.getInstance("USD");
        Product p = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://just/a/shortened/link.jpg");
        productDao.save(p);
        Long id = p.getId();
        assertNotNull(id);
        assertEquals(productDao.getProduct(p.getId()).get(), p);
        productDao.save(p);
        assertEquals(p.getId(), id);
    }
    @Test
    public void testGetProduct() {
        Currency usd = Currency.getInstance("USD");
        Product p = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://just/a/shortened/link.jpg");
        productDao.save(p);
        assertEquals(productDao.getProduct(p.getId()).get(), p);
    }
    @Test
    public void testSearchSimple() {
        ClearProductDao();
        Currency usd = Currency.getInstance("USD");
        Product p1 = new Product("sgs", "a", new BigDecimal(100), usd, 100, "somelink");
        Product p2 = new Product("sgs", "b", new BigDecimal(100), usd, 100, "somelink");
        Product p3 = new Product("sgs", "c", new BigDecimal(100), usd, 100, "somelink");
        productDao.save(p1);
        productDao.save(p2);
        productDao.save(p3);
        assertTrue(productDao.findProducts("a",null,null).contains(p1));
        assertFalse(productDao.findProducts("a", null, null).contains(p2));
        assertFalse(productDao.findProducts("a", null, null).contains(p3));
    }
    @Test
    public void testSearchAdvanced() {
        ClearProductDao();
        Currency usd = Currency.getInstance("USD");
        Product p1 = new Product("sgs", "a", new BigDecimal(100), usd, 100, "somelink");
        Product p2 = new Product("sgs", "a a", new BigDecimal(100), usd, 100, "somelink");
        Product p3 = new Product("sgs", "b", new BigDecimal(100), usd, 100, "somelink");
        Product p4 = new Product("sgs", "c", new BigDecimal(100), usd, 100, "somelink");
        productDao.save(p1);
        productDao.save(p2);
        productDao.save(p3);
        productDao.save(p4);
        assertTrue(productDao.findProducts("a a", null, null).contains(p1));
        assertTrue(productDao.findProducts("a a", null, null).contains(p2));
        assertEquals(productDao.findProducts("a a", null, null).get(0), p2);
        assertEquals(productDao.findProducts("a a", null, null).get(1), p1);
        assertFalse(productDao.findProducts("a", null, null).contains(p3));
        assertFalse(productDao.findProducts("a", null, null).contains(p4));
        assertFalse(productDao.findProducts(null, null, null).isEmpty());
    }

    @Test
    public void testSorting() {
        ClearProductDao();
        Currency usd = Currency.getInstance("USD");
        Product p4 = new Product("sgs", "c", new BigDecimal(4), usd, 100, "somelink");
        Product p1 = new Product("sgs", "a", new BigDecimal(1), usd, 100, "somelink");
        Product p3 = new Product("sgs", "b", new BigDecimal(3), usd, 100, "somelink");
        Product p2 = new Product("sgs", "ab", new BigDecimal(2), usd, 100, "somelink");
        productDao.save(p1);
        productDao.save(p2);
        productDao.save(p3);
        productDao.save(p4);

        List<Product> searchResult = productDao.findProducts(null, SortField.description, SortType.asc);
        assertEquals(searchResult.get(0), p1);
        assertEquals(searchResult.get(1), p2);
        assertEquals(searchResult.get(2), p3);
        assertEquals(searchResult.get(3), p4);

        searchResult = productDao.findProducts(null, SortField.description, SortType.desc);
        assertEquals(searchResult.get(0), p4);
        assertEquals(searchResult.get(1), p3);
        assertEquals(searchResult.get(2), p2);
        assertEquals(searchResult.get(3), p1);

        searchResult = productDao.findProducts(null, SortField.price, SortType.asc);
        assertEquals(searchResult.get(0), p1);
        assertEquals(searchResult.get(1), p2);
        assertEquals(searchResult.get(2), p3);
        assertEquals(searchResult.get(3), p4);

        searchResult = productDao.findProducts(null, SortField.price, SortType.desc);
        assertEquals(searchResult.get(0), p4);
        assertEquals(searchResult.get(1), p3);
        assertEquals(searchResult.get(2), p2);
        assertEquals(searchResult.get(3), p1);
    }

    @Test
    public void testPriceChangeHistory() {
        ClearProductDao();
        Currency usd = Currency.getInstance("USD");
        Product p = new Product("sgs", "a", new BigDecimal(1), usd, 100, "somelink");
        assertEquals(1, p.getPriceHistory().size());
        p.setPrice(new BigDecimal(100));
        assertEquals(2, p.getPriceHistory().size());
        assertEquals(p.getPriceHistory().get(0).getPrice(), new BigDecimal(1));
        assertEquals(p.getPriceHistory().get(1).getPrice(), new BigDecimal(100));
    }

    private void ClearProductDao() {
        List<Long> ids = new ArrayList<>();
        for(Product i : productDao.findProducts(null,null,null)) {
            ids.add(i.getId());
        }
        for(Long id : ids) {
            productDao.delete(id);
        }
    }
}
