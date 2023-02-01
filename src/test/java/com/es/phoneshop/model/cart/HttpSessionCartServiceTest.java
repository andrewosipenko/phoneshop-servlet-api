package com.es.phoneshop.model.cart;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.HttpSessionCartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private ProductDao productDao;
    private CartService cartService;

    @Before
    public void setup() {
        this.cartService = HttpSessionCartService.getInstance();

        this.productDao = ArrayListProductDao.getInstance();
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product1 = new Product("test1", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        productDao.save(product1);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(HttpSessionCartService.class.getName() + ".cart")).thenReturn(new Cart());
    }

    @Test
    public void testAddNewProduct() throws OutOfStockException {
        Product product = productDao.getProduct(1L);
        CartItem expectedCartItem = new CartItem(product, 1);

        cartService.add(product.getId(), 1, request);

        assertEquals(expectedCartItem, cartService.getCart(request).getItems().get(0));
    }

    @Test
    public void testAddExistedProduct() throws OutOfStockException {
        Product product = productDao.getProduct(1L);

        cartService.add(product.getId(), 1, request);
        cartService.add(product.getId(), 2, request);

        CartItem expectedCartItem = new CartItem(product, 3);

        assertEquals(expectedCartItem, cartService.getCart(request).getItems().get(0));
    }

    @Test(expected = OutOfStockException.class)
    public void testAddProductWithQuantityGreaterThanStock() throws OutOfStockException {
        Product product = productDao.getProduct(1L);

        cartService.add(product.getId(), product.getStock() + 1, request);
    }

    @Test
    public void testUpdateProduct() throws OutOfStockException {
        Product product = productDao.getProduct(1L);

        cartService.add(product.getId(), 1, request);
        cartService.update(product.getId(), 3, request);

        CartItem expectedCartItem = new CartItem(product, 3);

        Cart cart = cartService.getCart(request);

        assertEquals(expectedCartItem, cart.getItems().get(0));
        assertEquals(3, cart.getTotalQuantity());
        assertEquals(product.getPrice().multiply(BigDecimal.valueOf(3)), cart.getTotalCost());
    }

    @Test(expected = ProductNotFoundException.class)
    public void testUpdateNonAddedToTheCartProduct() throws OutOfStockException {
        Product product = productDao.getProduct(1L);

        cartService.update(product.getId(), 1, request);
    }

    @Test
    public void testDelete() throws OutOfStockException {
        Product product = productDao.getProduct(1L);
        Product product1 = productDao.getProduct(2L);

        cartService.add(product.getId(), 1, request);
        cartService.add(product1.getId(), 1, request);

        cartService.delete(2L, request);

        Cart cart = cartService.getCart(request);

        List<CartItem> expectedCartItems = new ArrayList<>();
        expectedCartItems.add(new CartItem(product, 1));

        assertEquals(expectedCartItems, cart.getItems());
        assertEquals(1, cart.getTotalQuantity());
        assertEquals(product.getPrice(), cart.getTotalCost());
    }
}
