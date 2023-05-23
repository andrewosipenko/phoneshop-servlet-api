package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.CartServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private CartService cartService;
    @Mock
    private ProductDao productDao;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    private Locale locale;
    private Cart cart;
    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();
    private static final String PRODUCT_ID_FROM_URL = "/1";
    private static final String QUANTITY_FROM_INPUT_FIELD = "1";
    private static final String QUANTITY_MORE_THAN_STOCK = "1000";
    private static final String NOT_INTEGER_QUANTITY = "ghjk";


    @Before
    public void setup() {
        servlet.init();
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        locale = new Locale("RUS");
        cart = new Cart();
        Currency usd = Currency.getInstance("USD");
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"), 1));
        cartItems.add(new CartItem(new Product(2L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"), 2));
        cartItems.add(new CartItem(new Product(3L, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"), 10));
        cart.setCartItems(cartItems);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        when(request.getPathInfo()).thenReturn(PRODUCT_ID_FROM_URL);
        when(productDao.getProduct(anyLong())).thenReturn(product);

        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException, ParseException {
        when(request.getPathInfo()).thenReturn(PRODUCT_ID_FROM_URL);
        when(request.getParameter(anyString())).thenReturn(QUANTITY_FROM_INPUT_FIELD);
        when(request.getLocale()).thenReturn(locale);

        servlet.doPost(request, response);

        verify(cartService).add(anyLong(), anyInt(), any());
    }

    @Test(expected = OutOfStockException.class)
    public void testExceptionForProductQuantityMoreThanStock() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn(PRODUCT_ID_FROM_URL);
        when(request.getParameter(anyString())).thenReturn(QUANTITY_MORE_THAN_STOCK);
        when(request.getLocale()).thenReturn(locale);

        servlet.doPost(request, response);
    }

    @Test(expected = NumberFormatException.class)
    public void testExceptionForProductQuantity() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn(PRODUCT_ID_FROM_URL);
        when(request.getParameter(anyString())).thenReturn(NOT_INTEGER_QUANTITY);
        when(request.getLocale()).thenReturn(locale);

        servlet.doPost(request, response);
    }
}
