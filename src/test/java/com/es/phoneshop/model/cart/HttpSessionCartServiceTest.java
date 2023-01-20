package com.es.phoneshop.model.cart;

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
        productDao.save(product);

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
}