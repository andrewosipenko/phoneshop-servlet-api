package com.es.phoneshop.model;

import org.junit.Before;


public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
    }

//    @Test
//    public void testFindProductsNoResults() {
//        Assert.assertFalse(productDao.findProducts(null, null,null).isEmpty());
//    }
//
//    @Test
//    public void testGetProduct0Null() {
//        Assert.assertNull(productDao.getProduct(0L));
//    }
//
//    @Test
//    public void testFindProductsWithStockAndPriceExpressions() {
//        Assert.assertEquals(productDao.findProducts(null, null,null).size(), 11);
//    }
//
//    @Test
//    public void testSaveExistObjectUpdate() {
//        Product existObject = productDao.getProduct(3L);
//        int oldAmount = productDao.findProducts(null, null,null).size();
//        productDao.save(existObject);
//        Assert.assertEquals(oldAmount, productDao.findProducts(null, null,null).size());
//    }
//
//    @Test
//    public void testSaveNewObject() {
//        Currency usd = Currency.getInstance("USD");
//        Price price = new Price(LocalDate.ofYearDay(2002,12),BigDecimal.valueOf(800));
//        List<Price> pricesNewProduct = new ArrayList<>();
//        pricesNewProduct.add(price);
//        Product newProduct = new Product(null, "xmin8pr", "xiaomi Redmi Note 8 Pro",
//                pricesNewProduct, usd, 3, null);
//        int oldAmount = productDao.findProducts(null, null,null).size();
//        productDao.save(newProduct);
//        Assert.assertEquals(oldAmount + 1, productDao.findProducts(null, null,null).size());
//    }
//
//    @Test
//    public void testDeleteNotExistObject() {
//        int oldAmount = productDao.findProducts(null, null,null).size();
//        productDao.delete(0L);
//        Assert.assertEquals(oldAmount, productDao.findProducts(null, null,null).size());
//    }
//
//    @Test
//    public void testDeleteExistObject() {
//        productDao.delete(1L);
//        Assert.assertNull(productDao.getProduct(1L));
//    }
}
