package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.impl.DefaultCartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteCartItemServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private DefaultCartService cartService;
    @Mock
    private Cart cart;

    @InjectMocks
    private DeleteCartItemServlet servlet = new DeleteCartItemServlet();

    @Before
    public void setup() {
        when(cartService.getCart(request)).thenReturn(cart);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getParameter("productId")).thenReturn("1");

        servlet.doPost(request, response);

        verify(cartService).delete(cart, 1L);
        verify(response).sendRedirect(anyString());
    }
}