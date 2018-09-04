package com.es.phoneshop.model;

import com.es.phoneshop.web.ProductIDGenerator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CartServiceTest {
    private CartService cartService = CartService.getInstance();
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

    @Mock
    private HttpSession session = mock(HttpSession.class);

    @Mock
    private HttpServletRequest request = mock(HttpServletRequest.class);

    @Before
    public void clear() {
        when(request.getSession()).thenReturn(session);
        cartService.getCart(request).getCartItems().clear();
    }

    @Test
    public void getCart() {
        when(request.getSession()).thenReturn(session);
        Cart cart = cartService.getCart(request);
        assertFalse(cart.getCartItems().isEmpty());
    }

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