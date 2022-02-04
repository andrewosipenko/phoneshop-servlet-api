package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CartTest {
    @Test
    public void testCart() {
        Currency usd = Currency.getInstance("USD");
        Product p1 = new Product("a", "a", new BigDecimal(1), usd, 100, "somelink");
        Product p2 = new Product("b", "b", new BigDecimal(1), usd, 100, "somelink");
        Product p3 = new Product("c", "c", new BigDecimal(1), usd, 100, "somelink");
        Cart cart = new Cart();
        cart.add(p1, 1);
        cart.add(p1, 1);
        cart.add(p2, 1);
        assertEquals(cart.get(p1), 2);
        assertEquals(cart.get(p2), 1);
        assertEquals(cart.get(p3), 0);
    }
}
