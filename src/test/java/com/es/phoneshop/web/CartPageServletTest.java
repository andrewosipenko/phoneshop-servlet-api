package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import static org.mockito.ArgumentMatchers.anyString;
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
    private HttpSession session;

    private CartPageServlet servlet;
    private ProductDao productDao;
    private CartService cartService;
    private Cart cart;
    private String[] productIds;
    private String[] quantities;


    @Before
    public void setup() throws ServletException {
        servlet = new CartPageServlet();
        servlet.init();
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        cart = new Cart();
        productIds= new String[]{"1"};
        quantities = new String[]{"5"};
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(cart);
        when(request.getLocale()).thenReturn(new Locale("en", "USA"));
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException, LackOfStockException {
        Currency usd = Currency.getInstance("USD");
        Locale locale = request.getLocale();
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 5, ""));
        when(request.getParameterValues("productId")).thenReturn(productIds);
        when(request.getParameterValues("quantity")).thenReturn(quantities);
        cartService.add(session, cart, 1L, String.valueOf(1), locale);
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }

}