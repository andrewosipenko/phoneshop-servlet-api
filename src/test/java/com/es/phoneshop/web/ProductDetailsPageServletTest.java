package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    private long id = 1;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletConfig config;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private HttpSession httpSession;

    private ProductDetailsPageServlet servlet;

    private Cart cart;

    private ProductDao productDao;

    @Before
    public void setup() throws ServletException {
        servlet = new ProductDetailsPageServlet();
        servlet.init();
        productDao = ArrayListProductDao.getInstance();
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 5, "");
        cart = new Cart();
        productDao.save(product);
        when(request.getPathInfo()).thenReturn("/" + productDao.getProduct(product.getId()).getId());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(httpSession);
        when(request.getLocale()).thenReturn(new Locale("en", "USA"));
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.init(config);
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws IOException, ServletException {
        servlet.init(config);
        when(request.getParameter("quantity")).thenReturn("1");
        when(httpSession.getAttribute(anyString())).thenReturn(cart);
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }
}
