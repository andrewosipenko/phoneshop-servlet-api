package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddToCartServletTest {

    private long id = 1;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private HttpSession session;

    @Mock
    private ServletConfig config;

    @Mock
    private Product product;

    private AddToCartServlet servlet;

    private Cart cart;

    @Before
    public void setup() throws ServletException {
        servlet = new AddToCartServlet();
        servlet.init(config);
        when(request.getLocale()).thenReturn(new Locale("en", "USA"));
        when(request.getPathInfo()).thenReturn("/" + id);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        cart = new Cart();
        when(session.getAttribute(anyString())).thenReturn(cart);

    }

    @Test
    public void testDoPost() throws IOException {
        //when(request.getParameter("productId")).thenReturn("1");
        //servlet.doPost(request, response);
        //verify(response).sendRedirect(anyString());
    }
}
