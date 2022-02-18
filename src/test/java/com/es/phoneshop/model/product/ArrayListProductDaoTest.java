package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        productDao.clearProductDao();
        saveSampleProducts();
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts("", null).isEmpty());
    }

    @Test
    public void testSaveNewProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        productDao.save(product);
        assertTrue(product.getId() > 0);
        assertTrue(productDao.getProduct(product.getId()).isPresent());
    }

    @Test
    public void testRewriteProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        productDao.save(product);

        Product replaceProduct = new Product(product.getId(), "replace", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        productDao.save(replaceProduct);

        Product result = productDao.getProduct(replaceProduct.getId()).get();
        assertEquals(result.getCode(), replaceProduct.getCode());
        assertTrue(productDao.getProduct(replaceProduct.getId()).isPresent());
        assertNotEquals(result.getCode(), product.getCode());
    }

    @Test
    public void testDeleteProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        productDao.save(product);
        productDao.delete(product.getId());
        assertFalse(productDao.getProduct(product.getId()).isPresent());
    }

    @Test
    public void testFindProducts() {
        assertTrue(productDao.findProducts("", null).stream()
                .noneMatch(product -> (product.getPrice() == null || product.getStock() <= 0)));
    }

    @Test
    public void testFindProductsByQuery() {
        String query = "samsung";
        assertTrue(productDao.findProducts(query, null).stream()
                .allMatch(product -> product.getDescription().toLowerCase().contains(query.toLowerCase())));
    }

    @Test
    public void sortingTest() {
        String query = "iphone";
        assertEquals(productDao.findProducts(query, null).size(),
                productDao.findProducts("", null).stream()
                        .filter(product -> product.getDescription().toLowerCase()
                                .contains(query)).count());
       // saveSampleProducts();
        List<Product> productList = productDao.findProducts("", SortingParams.priceAsc);
        for (int i = 0; i < productList.size() - 1; i++) {
            assertTrue(priceAsc(productList.get(i), productList.get(i + 1)));
        }
        productList = productDao.findProducts(query, SortingParams.priceDesc);
        for (int i = 0; i < productList.size() - 1; i++) {
            assertTrue(priceDesc(productList.get(i), productList.get(i + 1)));
        }
        productList = productDao.findProducts("",SortingParams.descriptionAsc);
        for (int i = 0; i < productList.size() - 1; i++) {
            assertTrue(descriptionAsc(productList.get(i), productList.get(i + 1)));
        }
        productList = productDao.findProducts(query,SortingParams.descriptionDesc);
        for (int i = 0; i < productList.size() - 1; i++) {
            assertTrue(descriptionDesc(productList.get(i), productList.get(i + 1)));
        }
        productList = productDao.findProducts(null,SortingParams.defaultSort);
        assertEquals(productDao.findProducts(null,null),productList);

    }

    private boolean priceAsc(Product left, Product right) {
        return left.getPrice().compareTo(right.getPrice()) <= 0;
    }

    private boolean priceDesc(Product left, Product right) {
        return left.getPrice().compareTo(right.getPrice()) >= 0;
    }

    private boolean descriptionAsc(Product left, Product right) {
        return left.getDescription().toLowerCase()
                .compareTo(right.getDescription().toLowerCase(Locale.ROOT)) <= 0;
    }

    private boolean descriptionDesc(Product left, Product right) {
        return left.getDescription().toLowerCase()
                .compareTo(right.getDescription().toLowerCase()) >= 0;
    }

    private void saveSampleProducts() {
        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productDao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "manufacturer/Apple/Apple%20iPhone.jpg"));
        productDao.save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "manufacturer/Apple/Apple%20iPhone%206.jpg"));
        productDao.save(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        productDao.save(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        productDao.save(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        productDao.save(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "manufacturer/Nokia/Nokia%203310.jpg"));
        productDao.save(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "manufacturer/Palm/Palm%20Pixi.jpg"));
        productDao.save(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "manufacturer/Siemens/Siemens%20C56.jpg"));
        productDao.save(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "manufacturer/Siemens/Siemens%20C61.jpg"));
        productDao.save(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "manufacturer/Siemens/Siemens%20SXG75.jpg"));
    }

    @Test
    public void sortingEnumIsExistMethodTest(){
        assertTrue(SortingParams.isExist("descriptionAsc"));
        assertTrue(SortingParams.isExist("descriptionDesc"));
        assertTrue(SortingParams.isExist("priceAsc"));
        assertTrue(SortingParams.isExist("priceDesc"));
        assertTrue(SortingParams.isExist("defaultSort"));
        assertFalse(SortingParams.isExist(null));
    }
}
