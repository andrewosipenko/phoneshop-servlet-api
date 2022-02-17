package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteCartItemServletTest {
    private Lock lock = new ReentrantLock();

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private HttpSession httpSession;
    private final DeleteCartItemServlet servlet = new DeleteCartItemServlet();

    @Before
    public void setup() throws ServletException {
        servlet.init(servletConfig);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/0");
        when(httpSession.getAttribute(HttpSessionCartService.class.getName() + ".lock")).thenReturn(lock);

        Locale locale = new Locale("ru");
        when(request.getSession()).thenReturn(httpSession);

        Cart cart = new Cart();
        when(httpSession.getAttribute(HttpSessionCartService.class.getName() + ".cart")).thenReturn(cart);
        Currency usd = Currency.getInstance("USD");
        Product p = new Product("a", "a", new BigDecimal(1), usd, 100, "somelink");
        ProductDao productDao = ArrayListProductDao.getInstance();
        productDao.save(p);
        servlet.doPost(request,response);
        assertFalse(cart.getItems().containsKey(p));
    }
}
