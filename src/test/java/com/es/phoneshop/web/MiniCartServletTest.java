package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MiniCartServletTest {
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

    private MiniCartServlet servlet = new MiniCartServlet();

    @Before
    public void setUp() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        httpSession = Mockito.mock(HttpSession.class);
        when(request.getSession()).thenReturn(httpSession);
        servlet.setCartService(cartService);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Cart cart = new Cart();

        when(cartService.getCart(request)).thenReturn(cart);
        when(httpSession.getAttribute("cart")).thenReturn(cart);

        servlet.doGet(request, response);

        verify(request).setAttribute("cart", cart);
        verify(requestDispatcher).include(request, response);
    }
}
