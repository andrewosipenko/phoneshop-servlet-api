package com.es.phoneshop.model;


import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.Assert.*;


public class CartServiceTest {
    private CartService cartService = CartService.getInstance();

    @Test
    public void getInstance() throws Exception {
        CartService instance = CartService.getInstance();

        assertNotNull(instance);
    }

    @Test
    public void getCart() throws Exception {
    }

    @Test
    public void add() throws Exception {
        Cart cart = new Cart();
        Product product = new Product(1L, "code1", "decription1", new BigDecimal(10), Currency.getInstance(Locale.US), 1);

        cartService.add(cart, product, 1);

        assertFalse(cart.getCartItems().isEmpty());
    }
}
