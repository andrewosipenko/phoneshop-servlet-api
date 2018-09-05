package com.es.phoneshop.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

public class CartServiceImplTest {
    private CartServiceImpl cartService;

    @Mock
    Cart cart = Mockito.mock(Cart.class);

    @Mock
    CartItem cartItem = Mockito.mock(CartItem.class);

    @Mock
    Product product = Mockito.mock(Product.class);

    @Mock
    HttpServletRequest requestMock = Mockito.mock(HttpServletRequest.class);
    @Mock
    HttpSession sessionMock = Mockito.mock(HttpSession.class);

    @Before
    public void setUp() throws Exception {
        cartService = CartServiceImpl.getInstance();
        Mockito.when(requestMock.getSession()).thenReturn(sessionMock);
        Mockito.when(sessionMock.getAttribute("cart")).thenReturn(cart);
    }

    @AfterAll
    public void clear(){
        cart.getCartItems().clear();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(cartService);
    }

    @Test
    public void testGetCart(){
        assertEquals(cart, cartService.getCart(requestMock));
    }

    @Test
    public void testAdd() {
        //Cart cart = new Cart();
        Mockito.when(product.getStock()).thenReturn(10);
        Mockito.when(cartItem.getProduct()).thenReturn(product);
        cartService.add(cart, cartItem.getProduct(), cartItem.getQuantity());
        assertFalse(cart.getCartItems().isEmpty());
    }
}