package com.es.phoneshop.model.product.service;

import com.es.phoneshop.model.product.bean.Cart;
import com.es.phoneshop.model.product.bean.CartItem;
import com.es.phoneshop.model.product.bean.PriceHistory;
import com.es.phoneshop.model.product.bean.Product;
import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.ProductDao;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceImplTest extends TestCase {
    private Cart cart;
    private CartService cartService = CartServiceImpl.getInstance();
    private Product product;
    private ProductDao arrayListProductDao;
    @Mock
    private HttpSession httpSession;

    @Before
    public void setup() {
        cart = new Cart();
        Currency usd = Currency.getInstance("USD");
        product = new Product("sgs", "Samsung Galaxy S", new PriceHistory(LocalDate.now(),
                new BigDecimal(100)), usd, 100,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/" +
                        "Samsung/Samsung%20Galaxy%20S.jpg");
        arrayListProductDao = ArrayListProductDao.getInstance();
        arrayListProductDao.save(product);
    }


    @Test
    public void givenHttpSession_WhenGetCart_ThenReturnNewCart() {
        when(httpSession.getAttribute(anyString())).thenReturn(null);

        Cart result = cartService.getCart(httpSession);

        verify(httpSession).getAttribute(anyString());
        verify(httpSession).setAttribute(anyString(), any());
        assertNotNull(result);
        assertNotEquals(cart, result);
    }

    @Test
    public void givenHttpSessionWithOldCart_WhenGetCart_ThenReturnOldCart() {
        when(httpSession.getAttribute(anyString())).thenReturn(cart);

        Cart result = cartService.getCart(httpSession);

        verify(httpSession).getAttribute(anyString());
        assertNotNull(result);
        assertEquals(cart, result);
    }

    @Test
    public void givenCartWithProductIdWithQuantityWithFormatRU_WhenAdd_ThenAddingToCart()
            throws QuantityException {
        Locale locale = new Locale("ru", "RU");
        NumberFormat format = NumberFormat.getInstance(locale);
        Cart cartExpected = new Cart();
        List<CartItem> cartItemList = cartExpected.getItems();
        cartItemList.add(new CartItem(product.getId(), 1));

        cartService.add(cart, product.getId(), "1,000", format);

        assertEquals(cartExpected, cart);
    }

    @Test(expected = QuantityException.class)
    public void givenCartWithProductIdWithQuantityOutOfStockWithFormatEN_WhenAdd_ThenAddingToCart()
            throws QuantityException {
        Locale locale = new Locale("en", "EN");
        NumberFormat format = NumberFormat.getInstance(locale);

        cartService.add(cart, product.getId(), "1,000", format);
    }

    @Test(expected = QuantityException.class)
    public void givenCartWithProductIdWithQuantityOutOfStockWithFormatRU_WhenAdd_ThenAddingToCart()
            throws QuantityException {
        Locale locale = new Locale("ru", "RU");
        NumberFormat format = NumberFormat.getInstance(locale);

        cartService.add(cart, product.getId(), "109", format);
    }

    @Test(expected = QuantityException.class)
    public void givenCartWithProductIdWithQuantityNotNumberWithFormatRU_WhenAdd_ThenAddingToCart()
            throws QuantityException {
        Locale locale = new Locale("ru", "RU");
        NumberFormat format = NumberFormat.getInstance(locale);

        cartService.add(cart, product.getId(), "hhsdhfsdf", format);
    }
}