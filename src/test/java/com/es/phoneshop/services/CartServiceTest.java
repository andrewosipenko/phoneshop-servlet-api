package com.es.phoneshop.services;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exceptions.NotEnoughElementsException;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.services.impl.CartServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession httpSession;

    @Mock
    private Cart cart;

    @Mock
    private ProductDao productDao;

    @Mock
    private Product product;

    @InjectMocks
    CartService cartService = CartServiceImpl.getInstance();

    public static final Long DEFAULT_PRODUCT_ID = 1L;
    public static final Long NEGATIVE_QUANTITY = -5L;
    public static final Long DEFAULT_QUANTITY = 10L;
    public static final int AMOUNT_ITEMS_THIS_PRODUCT_IN_SHOP = 3;

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(CartServiceImpl.CART_SESSION)).thenReturn(cart);
    }

    @Test
    public void testGetCartWhenCartIsNull() {
        when(httpSession.getAttribute(CartServiceImpl.CART_SESSION)).thenReturn(null);
        cartService.getCart(request);
        verify(httpSession).setAttribute(eq(CartServiceImpl.CART_SESSION), any(Cart.class));
    }

    @Test
    public void testGetCartWhenCartIsNotNull() {
        cartService.getCart(request);
        verify(httpSession, never()).setAttribute(eq(CartServiceImpl.CART_SESSION), any(Cart.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateWithNegativeQuantity() {
        cartService.update(cart, DEFAULT_PRODUCT_ID, NEGATIVE_QUANTITY);
    }

    @Test(expected = NotEnoughElementsException.class)
    public void testUpdateWhereQuantityIsBig() {
        when(productDao.getById(DEFAULT_PRODUCT_ID)).thenReturn(product);
        when(product.getStock()).thenReturn(AMOUNT_ITEMS_THIS_PRODUCT_IN_SHOP);
        cartService.update(cart, DEFAULT_PRODUCT_ID, DEFAULT_QUANTITY);

    }
}
