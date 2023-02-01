package com.es.phoneshop.model;

import com.es.phoneshop.model.cart.*;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

    private CartService cartService;

    private Cart cart;

    private Product product;

    private ProductDao productDao;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession session;

    @Before
    public void setup() {
        cartService = HttpSessionCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
        cart = new Cart();

        Currency currency = java.util.Currency.getInstance("USD");
        product = new Product(1L, "test", "HTC EVO Shift 4G", new BigDecimal(320), currency, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        productDao.save(product);

        cart.getCartItems().add(new CartItem(product, 1));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(cart);
    }

    @Test
    public void testAddExistingProduct() throws OutOfStockException {
        cartService.add(request, 1L, 1);
        assertEquals(1, cart.getCartItems().size());
    }

    @Test(expected = OutOfStockException.class)
    public void testAddMoreThenInStock() throws OutOfStockException {
        cartService.add(request, 1L, 10);
    }

    @Test
    public void testAddNewProduct() throws OutOfStockException {
        Currency currency = java.util.Currency.getInstance("USD");
        product = new Product(2L, "test", "HTC EVO Shift 4G", new BigDecimal(320), currency, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        productDao.save(product);

        cartService.add(request, 2L, 1);
        assertEquals(2, cart.getCartItems().size());
    }

    @Test
    public void testUpdateProduct() throws OutOfStockException {
        cartService.update(request, 1L, 2);

        assertEquals(2, cart.getCartItems().get(0).getQuantity());
    }

    @Test(expected = OutOfStockException.class)
    public void testUpdateMoreThanInStock() throws OutOfStockException {
        cartService.update(request, 1L, 1000);
    }

    @Test
    public void testCalculationOfTotalQuantity() throws OutOfStockException {
        Currency currency = java.util.Currency.getInstance("USD");
        product = new Product(2L, "test", "HTC EVO Shift 4G", new BigDecimal(320), currency, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        productDao.save(product);
        cartService.add(request, 2L, 1);

        assertEquals(new BigDecimal(640), cartService.getCart(request).getTotalCost());
    }

    @Test
    public void testCalculationOfTotalCost() throws OutOfStockException {
        Currency currency = java.util.Currency.getInstance("USD");
        product = new Product(2L, "test", "HTC EVO Shift 4G", new BigDecimal(320), currency, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        productDao.save(product);
        cartService.add(request, 2L, 1);

        assertEquals(2, cartService.getCart(request).getTotalQuantity());
    }

    @Test
    public void testDeleteExistingProduct() {
        cartService.delete(request, 1L);
        assertTrue(cart.getCartItems().isEmpty());
    }

    @Test
    public void testDeleteNonExistingProduct() {
        int size = cart.getCartItems().size();
        cartService.delete(request, 2L);
        assertEquals(size, cart.getCartItems().size());
    }


}
