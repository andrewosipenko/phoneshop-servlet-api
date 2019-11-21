package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteCartItemServletTest {

    @Mock
    HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession httpSession;
    @Mock
    private CartService cartService;
    @Mock
    private ProductDao productDao;

    private DeleteCartItemServlet servlet = new DeleteCartItemServlet();

    @Before
    public void setUp() {
        servlet.setCartService(cartService);
        servlet.setProductDao(productDao);
        httpSession = Mockito.mock(HttpSession.class);
        when(request.getSession()).thenReturn(httpSession);
    }

    @Test
    public void testDoPostSuccessfully() throws IOException {
        when(request.getParameter("productId")).thenReturn("1");

        servlet.doPost(request, response);

        verify(request).getParameter("productId");
        verify(response).sendRedirect(anyString());
    }
}
