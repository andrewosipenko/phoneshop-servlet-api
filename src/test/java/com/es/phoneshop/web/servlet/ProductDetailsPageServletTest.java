package com.es.phoneshop.web.servlet;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.cartService.CartService;
import com.es.phoneshop.model.product.Product;
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
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig config;
    @Mock
    private Product product;
    @Mock
    private HttpSession session;
    @Mock
    private Cart cart;
    @Mock
    private CartService cartService;

    private final ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();
    private final ProductDao productDao = ArrayListProductDao.getInstance();

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        when(product.getId()).thenReturn(null);
        productDao.save(product);
        when(product.getId()).thenReturn(1L);

        when(request.getPathInfo()).thenReturn("/1");

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(anyString())).thenReturn(cart);

        when(request.getLocale()).thenReturn(Locale.getDefault());

        when(product.getStock()).thenReturn(100);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute(eq("recentlyViewed"), any());
        verify(request).setAttribute(eq("product"), any());
        verify(request).setAttribute(eq("cart"), any());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostCorrectQuantity() throws ServletException, IOException {
        when(request.getParameter("quantity")).thenReturn("3");

        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostIncorrectQuantity() throws ServletException, IOException {
        when(request.getParameter("quantity")).thenReturn(anyString());

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), anyString());
    }
}
