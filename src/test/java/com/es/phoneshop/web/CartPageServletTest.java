package com.es.phoneshop.web;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.CartService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private CartService cartService;
    private CartPageServlet servlet = new CartPageServlet();
    private Locale locale = new Locale("RUS");
    private Cart cart = new Cart();
    private static final String PRODUCT_ID_FROM_URL = "/1";
    private static final String QUANTITY_MORE_THAN_STOCK = "1000";
    private static final String NOT_INTEGER_QUANTITY = "ghjk";
    private static final String[] PRODUCT_IDS = { "2L"};
    private static final String[] QUANTITIES = { "2"};

    @Before
    public void setup() {
        servlet.init();
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(2L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        cart.getCartItems().add(new CartItem(product, 2));
    }

    @Test
    public void doGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute(anyString(), anyList());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPost() throws ServletException, IOException {
        when(request.getParameterValues(anyString())).thenReturn(PRODUCT_IDS);
        when(request.getParameterValues(anyString())).thenReturn(QUANTITIES);
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getLocale()).thenReturn(locale);

        servlet.doPost(request, response);

        verify(cartService).update(anyLong(), anyInt(), cart);
        verify(response).sendRedirect(anyString());
    }

    @Test(expected = OutOfStockException.class)
    public void testExceptionForProductQuantityMoreThanStock() throws ServletException, IOException {
        when(request.getParameterValues(anyString())).thenReturn(PRODUCT_IDS);
        when(request.getParameterValues(anyString())).thenReturn(QUANTITIES);
        when(request.getLocale()).thenReturn(locale);

        servlet.doPost(request, response);
    }

    @Test(expected = NumberFormatException.class)
    public void testExceptionForProductQuantity() throws ServletException, IOException {
        when(request.getParameterValues(anyString())).thenReturn(PRODUCT_IDS);
        when(request.getParameterValues(anyString())).thenReturn(QUANTITIES);
        when(request.getLocale()).thenReturn(locale);

        servlet.doPost(request, response);
    }
}
