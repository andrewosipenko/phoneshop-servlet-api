package com.es.phoneshop.model.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)

public class ProductTest
{
    @Test
    public void testPriceChangeHistory() {
        Currency usd = Currency.getInstance("USD");
        Product p = new Product("sgs", "a", new BigDecimal(1), usd, 100, "somelink");
        assertEquals(1, p.getPriceHistory().size());
        p.setPrice(new BigDecimal(100));
        assertEquals(2, p.getPriceHistory().size());
        assertEquals(p.getPriceHistory().get(0).getPrice(), new BigDecimal(1));
        assertEquals(p.getPriceHistory().get(1).getPrice(), new BigDecimal(100));
    }
}
