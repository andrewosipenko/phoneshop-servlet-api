package com.es.phoneshop.cart;

import com.es.phoneshop.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

public class CartServiceTest {
    private CartService cartService = CartService.getInstance();

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
        assertTrue(cart.getCartItems().isEmpty());
    }

    @Test
    public void add() {
        Cart cart = new Cart();
        Product product = mock(Product.class);
        when(product.getStock()).thenReturn(2);
        CartItem cartItem = new CartItem(product, 1);
        cartService.add(cart, cartItem.getProduct(), cartItem.getQuantity());
        assertTrue(cart.getCartItems().contains(cartItem));
    }

    @Test
    public void addExisted() {
        Cart cart = new Cart();
        Product product = mock(Product.class);
        when(product.getStock()).thenReturn(2);
        CartItem cartItem = new CartItem(product, 1);
        cart.getCartItems().add(cartItem);
        cartService.add(cart, cartItem.getProduct(), 1);
        assertTrue(cart.getCartItems().get(cart.getCartItems().indexOf(cartItem)).getQuantity()==2);
    }

    @Test
    public void addOutOfStock() {
        Cart cart = new Cart();
        Product product = mock(Product.class);
        when(product.getStock()).thenReturn(2);
        CartItem cartItem = new CartItem(product, 2);
        cart.getCartItems().add(cartItem);
        cartService.add(cart, cartItem.getProduct(), 3);
        assertTrue(cart.getCartItems().get(cart.getCartItems().indexOf(cartItem)).getQuantity()==2);
    }
}