package com.es.phoneshop.model.product.dao.product;

import com.es.phoneshop.model.product.enums.SortBy;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ArrayListProductDaoTest {
    private static ProductDao productDao;

    @BeforeClass
    public static void start() {
        ArrayListProductDao.getInstance().setProducts(new ArrayList<>());
        productDao = ArrayListProductDao.getInstance();
    }


    @Test(expected = NullPointerException.class)
    public void testGetProductNullPointer() throws ProductNotFoundException {
        productDao.getProduct(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProductIllegalArgument() throws ProductNotFoundException {
        productDao.getProduct(-5L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProduct() throws ProductNotFoundException {
        Product product = new Product();
        product.setId(1L);
        productDao.save(product);
        assertEquals(product.getId(), productDao.getProduct(product.getId()).getId());
    }


    @Test(expected = NullPointerException.class)
    public void testDeleteProductNullPointer() throws ProductNotFoundException {
        productDao.delete(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteProductIllegalArgument() throws ProductNotFoundException {
        productDao.delete(-5L);
    }


    @Test(expected = NullPointerException.class)
    public void testSaveProductNullPointer() {
        productDao.save(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductIllegalArgument() {
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(1L); // setting up same id
        productDao.save(product1);
        productDao.save(product2);
    }

    @Test
    public void testSaveProduct() {
        Product product = new Product();
        product.setStock(2);
        product.setPrice(new BigDecimal(1.0));
        productDao.save(product);
        assertEquals(1, productDao.findProducts().size());
    }

    @Test
    public void testFindProductWithCondition() {
        Product negativeStockProduct = new Product();
        negativeStockProduct.setStock(-5);
        negativeStockProduct.setId(1L);
        Product nullPriceProduct = new Product();
        nullPriceProduct.setPrice(null);
        nullPriceProduct.setId(2L);
        productDao.save(negativeStockProduct);
        productDao.save(nullPriceProduct);
        assertEquals(0, productDao.findProducts().size());
    }

    @Test
    public void testFindProductByQuery() {
        long samsungCount = productDao.findProducts().stream()
                .filter(product -> product.getDescription().toLowerCase().contains("samsung"))
                .count();
        long iphoneCount = productDao.findProducts().stream()
                .filter(product -> product.getDescription().toLowerCase().contains("iphone"))
                .count();

        String query = " samsung   iphone  ";
        assertEquals(productDao.findProducts(query).size(), (samsungCount + iphoneCount));
    }

    @Test
    public void testSortProductsByDescriptionAsc() {
        List<Product> products = new ArrayList<>();
        Product productA = new Product();
        productA.setDescription("a");
        Product productB = new Product();
        productB.setDescription("b");
        products.add(productB);
        products.add(productA);
        String query = "a b";
        productDao.findProducts(query, SortBy.DESCRIPTION, true);
        assertEquals(2, products.size());
    }

    @Test
    public void testSortProductsByDescriptionDesc() {
        List<Product> products = new ArrayList<>();
        Product productA = new Product();
        productA.setDescription("a");
        Product productB = new Product();
        productB.setDescription("b");
        products.add(productA);
        products.add(productB);
        String query = "a b";
        productDao.findProducts(query, SortBy.DESCRIPTION, false);
        assertEquals(2, products.size());
    }

    @Test
    public void testSortProductsByPriceAsc() {
        List<Product> products = new ArrayList<>();
        Product productA = new Product();
        productA.setPrice(BigDecimal.ONE);
        productA.setDescription("a");
        Product productB = new Product();
        productB.setPrice(BigDecimal.TEN);
        productB.setDescription("b");
        products.add(productB);
        products.add(productA);
        String query = "a b";
        productDao.findProducts(query, SortBy.PRICE, true);
        assertEquals(2, products.size());
    }

    @Test
    public void testSortProductsByPriceDesc() {
        List<Product> products = new ArrayList<>();
        Product productA = new Product();
        productA.setPrice(BigDecimal.ONE);
        productA.setDescription("a");
        Product productB = new Product();
        productB.setPrice(BigDecimal.TEN);
        productB.setDescription("b");
        products.add(productA);
        products.add(productB);
        String query = "a b";
        productDao.findProducts(query, SortBy.PRICE, false);
        assertEquals(2, products.size());
    }

}
