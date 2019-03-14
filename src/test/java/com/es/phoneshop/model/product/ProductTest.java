package com.es.phoneshop.model.product;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;

public class ProductTest {

    @Test
    public void testProductInitializer() {
        Long id = 1L;
        String code = "1";
        String description = "1";
        BigDecimal price = new BigDecimal(1.0d);
        Currency currency = Currency.getInstance("USD");
        int stock = 1;
        String imageURL = "url";
        Product product = new Product(id, code, description, price, currency, stock, imageURL);
        assertEquals(id, product.getId());
        assertEquals(code, product.getCode());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(currency.getCurrencyCode(), product.getCurrency().getCurrencyCode());
        assertEquals(stock, product.getStock());
        assertEquals(imageURL, product.getImageUrl());
    }

    @Test
    public void testSetId() {
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        assertEquals(id, product.getId());
    }

    @Test
    public void testSetCode() {
        String code = "1";
        Product product = new Product();
        product.setCode(code);
        assertEquals(code, product.getCode());
    }

    @Test
    public void testSetDescription() {
        String description = "1";
        Product product = new Product();
        product.setDescription(description);
        assertEquals(description, product.getDescription());
    }

    @Test
    public void testSetPrice() {
        BigDecimal price = new BigDecimal(1.0d);
        Product product = new Product();
        product.setPrice(price);
        assertEquals(price, product.getPrice());
    }

    @Test
    public void testSetCurrency() {
        Currency currency = Currency.getInstance("USD");
        Product product = new Product();
        product.setCurrency(currency);
        assertEquals(currency.getCurrencyCode(), product.getCurrency().getCurrencyCode());
    }

    @Test
    public void testSetStock() {
        int stock = 1;
        Product product = new Product();
        product.setStock(stock);
        assertEquals(stock, product.getStock());
    }

    @Test
    public void testSetImageURL() {
        String imageURL = "1";
        Product product = new Product();
        product.setImageUrl(imageURL);
        assertEquals(imageURL, product.getImageUrl());
    }

}
