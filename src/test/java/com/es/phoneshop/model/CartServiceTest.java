package com.es.phoneshop.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CartServiceTest {
    private CartService service = CartService.getInstance();
    @Test
    public void testGetInstance() {
        CartService cartService = CartService.getInstance();

        assertNotNull(cartService);
    }

    @Test
    public void getCart() {

    }

    @Test
    public void testAdd() {
        Cart cart = new Cart();
        
        service.add(cart, ArrayListProductDao.getInstance().findProducts().get(0), 1);

        assertFalse(cart.getCartItems().isEmpty());
    }
}