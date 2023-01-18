package com.es.phoneshop.service;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.model.product.exception.OutOfStockException;
import com.es.phoneshop.model.product.service.CartService;
import com.es.phoneshop.model.product.service.impl.CartServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceImplTest {

    private CartService cartService;
    private ProductDao productDao;

    private HttpServletRequest requestMock;


    @Before
    public void init() {
        cartService = CartServiceImpl.getInstance();
        productDao = ArrayListProductDao.getInstance();
        productDao.save(new Product("prod", "description", new BigDecimal(123), Currency.getInstance("USD"), 35, null, null));

        requestMock = Mockito.mock(HttpServletRequest.class);
        HttpSession sessionMock = Mockito.mock(HttpSession.class);
        Mockito.when(requestMock.getSession()).thenReturn(sessionMock);
    }

    @Test
    public void testAddingProductToCart() throws OutOfStockException {
        Cart cart = new Cart();
        Assert.assertTrue(cart.getItems().isEmpty());
        cartService.add(cart, 1L,34, requestMock);
        Assert.assertFalse(cart.getItems().isEmpty());
    }

    @Test
    public void testGettingCart()
    {
        Cart cart = cartService.getCart(requestMock);
        Assert.assertNotNull(cart);
    }
    @Test
    public void testAddingSameProductsIncreaseQuantityOfSameProduct() throws OutOfStockException {
        Cart cart = new Cart();
        cartService.add(cart, 1L,10, requestMock);
        cartService.add(cart, 1L,5, requestMock);

        Assert.assertEquals(15, cart.getItems().get(0).getQuantity());
    }


}
