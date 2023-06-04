package com.es.phoneshop.service.impl;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.any;

public class CartServiceImplTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private Cart cart;

    @Mock
    private HttpSession session;

    @Mock
    private CartServiceImpl cartService;

    @Mock
    private ProductService productService;

    private Product product;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cartService = CartServiceImpl.getInstance();
        productService = mock(ProductService.class);
        product = new Product("TestProduct", "TestBrand", null, null, 10, null);
        when(productService.getProduct(anyLong())).thenReturn(product);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute(cartService.getCartSessionAttribute())).thenReturn(cart);
    }

    @Test
    public void givenNotPresentedCart_whenGetCart_thenReturnNull() {
        when(session.getAttribute(cartService.getCartSessionAttribute())).thenReturn(null);

        Cart cart = cartService.getCart(request);

        assertNull(cart);
    }

    @Test
    public void givenPresentedCart_whenGetCart_thenGiveCart() {
        Cart resultCart = cartService.getCart(request);

        assertSame(cart, resultCart);
        verify(request.getSession(), never()).setAttribute(eq(cartService.getCartSessionAttribute()), any(Cart.class));
    }

    @Test
    public void testValidProduct_whenAddToCart_thenCorrectAddToCart() throws OutOfStockException {
        Product product = new Product();
        product.setStock(10);
        Cart cart = new Cart();
        when(session.getAttribute(anyString())).thenReturn(cart);

        cartService.addToCart(product, 5, request);

        assertEquals(1, cart.getItems().size());
        CartItem cartItem = cart.getItems().get(0);
        assertEquals(product, cartItem.getProduct());
        assertEquals(5, cartItem.getQuantity());
    }

    @Test(expected = OutOfStockException.class)
    public void givenInvalidStockCart_whenAdd_thenThrowOutOfStockException() throws OutOfStockException {
        product.setStock(5);
        int quantity = 10;

        cartService.addToCart(productService.getProduct(1L), quantity, request);
    }
}

