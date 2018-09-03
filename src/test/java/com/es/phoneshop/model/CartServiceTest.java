package com.es.phoneshop.model;

import com.es.phoneshop.web.ProductIDGenerator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CartServiceTest {
    private CartService cartService = CartService.getInstance();
    private HttpServletRequest testHttpServletRequest = null;
    private static ProductDao productDao;

    @BeforeClass
    public static void init() {
        productDao = ArrayListProductDao.getInstance();
        Product product;
        for (int i = 0; i < 2; i++) {
            product = new Product();
            ProductIDGenerator.generateID(product);
            product.setCode(i + "");
            product.setDescription("description" + i);
            product.setPrice(new BigDecimal(i));
            product.setStock(i);
            productDao.save(product);
        }
    }

    /*@Before
    public void clear() {
        cartService.getCart(testHttpServletRequest).getCartItems().clear();
    }*/

    /*@Test
    public void getCart() {
        Cart cart = cartService.getCart(testHttpServletRequest);
        assertTrue(cart.getCartItems());
    }*/

    @Test
    public void add() {
        Cart cart = new Cart();
        CartItem cartItem = new CartItem(productDao.getProduct((long) 1),1);
        cartService.add(cart, cartItem.getProduct(), cartItem.getQuantity());
        assertTrue(cart.getCartItems().contains(cartItem));
    }

    @Test
    public void add2() {
        Cart cart = new Cart();
        CartItem cartItem = new CartItem(productDao.getProduct((long) 0),0);
        cartService.add(cart, cartItem.getProduct(), cartItem.getQuantity());
        assertFalse(cart.getCartItems().contains(cartItem));
    }
}