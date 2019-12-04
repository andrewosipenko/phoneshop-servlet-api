package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {

    @Mock
    HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private HttpSession httpSession;
    @Mock
    private CartService cartService;
    @Mock
    private ProductDao productDao;

    private CartPageServlet servlet = new CartPageServlet();

    @Before
    public void setUp() {
        servlet.setCartService(cartService);
        servlet.setProductDao(productDao);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        httpSession = Mockito.mock(HttpSession.class);
        when(request.getSession()).thenReturn(httpSession);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Cart cart = new Cart();
        when(cartService.getCart(request)).thenReturn(cart);

        servlet.doGet(request, response);

        verify(request).setAttribute("cart", cart);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostSuccessfully() throws ServletException, IOException {
        when(request.getParameterValues("productId")).thenReturn(new String[]{"1", "2"});
        when(request.getParameterValues("quantity")).thenReturn(new String[]{"1", "1"});

        when(cartService.getCart(request)).thenReturn(new Cart());
        when(request.getLocale()).thenReturn(new Locale("en"));

        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostWithNotANumberError() throws ServletException, IOException {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Cart cart = new Cart();
        Map<Product, String> errorMap = new HashMap<>();
        errorMap.put(product, "Not a number");

        when(request.getParameterValues("productId")).thenReturn(new String[]{"1", "2"});
        when(request.getParameterValues("quantity")).thenReturn(new String[]{"eee", "1"});

        when(productDao.getProduct(1L)).thenReturn(product);
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getLocale()).thenReturn(new Locale("en"));

        servlet.doPost(request, response);

        verify(request).setAttribute("errorMap", errorMap);
        verify(request).setAttribute("cart", cart);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostWithOutOfStockError() throws ServletException, IOException {
        CartService cartService = HttpSessionCartService.getInstance();
        servlet.setCartService(cartService);

        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product2 = new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");

        Map<Product, String> errorMap = new HashMap<>();
        errorMap.put(product1, "Not enough stock. Available " + product1.getStock());

        when(request.getParameterValues("productId")).thenReturn(new String[]{"1", "2"});
        when(request.getParameterValues("quantity")).thenReturn(new String[]{"100000", "1"});

        when(productDao.getProduct(1L)).thenReturn(product1);
        when(productDao.getProduct(2L)).thenReturn(product2);
        when(request.getLocale()).thenReturn(new Locale("en"));

        servlet.doPost(request, response);

        verify(request).setAttribute("errorMap", errorMap);
        verify(requestDispatcher).forward(request, response);
    }


}
