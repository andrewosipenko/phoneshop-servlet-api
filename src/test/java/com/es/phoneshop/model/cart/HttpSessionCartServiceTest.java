package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.IllegalQuantityException;
import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @Mock
    private HttpSession httpSession;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Product product;
    private CartService cartService;

    @Before
    public void setup() {
        cartService = HttpSessionCartService.getInstance();
        when(request.getSession()).thenReturn(httpSession);
        when(product.getStock()).thenReturn(10);
        when(product.getPrice()).thenReturn(new BigDecimal(100));
    }

    @Test
    public void testGetExistingCart() {
        Cart cart = new Cart();
        when(httpSession.getAttribute(anyString())).thenReturn(cart);
        Cart cartFromSession = cartService.getCart(httpSession);
        assertEquals(cart, cartFromSession);
    }

    @Test
    public void testGetNotExistingCart() {
        Cart cart = cartService.getCart(httpSession);
        assertNotNull(cart);
    }

    @Test(expected = LackOfStockException.class)
    public void testAddWithStockLessQuantity() throws LackOfStockException,
            IllegalQuantityException, NumberFormatException {
        cartService.add(httpSession, new Cart(), product.getId(), String.valueOf(15), request.getLocale());
    }

    @Test
    public void testAddWithoutSameCartItem() throws LackOfStockException, IllegalQuantityException {
        Cart cart = new Cart();
        cartService.add(httpSession, cart, product.getId(), String.valueOf(8), request.getLocale());
        assertFalse(cart.getCartItems().isEmpty());
    }

    @Test
    public void testAddWithSameCartItem() throws LackOfStockException, IllegalQuantityException {
        CartItem cartItem = new CartItem(product, 1);
        Cart cart = new Cart();
        cart.getCartItems().add(cartItem);
        String quantity = String.valueOf(8);
        cartService.add(httpSession, cart, product.getId(), quantity, request.getLocale());
        assertEquals(cartItem.getQuantity(), (Integer.parseInt(quantity) + 1));
    }

    @Test(expected = LackOfStockException.class)
    public void testAddWithSameCartItemAndStockLessQuantity() throws LackOfStockException,
            IllegalQuantityException, NumberFormatException {
        CartItem cartItem = new CartItem(product, 3);
        Cart cart = new Cart();
        cart.getCartItems().add(cartItem);
        cartService.add(httpSession, cart, product.getId(), String.valueOf(8), request.getLocale());
    }

    @Test
    public void testCalculateTotalPrice() {
        Cart cart = new Cart();
        cart.getCartItems().add(new CartItem(product, 1));
        cart.getCartItems().add(new CartItem(product, 2));
        cartService.calculateTotalPrice(cart);
        assertEquals(new BigDecimal(300), cart.getTotalPrice());
    }

  @Test
    public void testCalculateTotalQuantity(){
      Cart cart = new Cart();
      cart.getCartItems().add(new CartItem(product, 3));
      cart.getCartItems().add(new CartItem(product, 2));
      cartService.calculateTotalQuantity(cart);
      assertEquals(5, cart.getTotalQuantity());
  }
}
