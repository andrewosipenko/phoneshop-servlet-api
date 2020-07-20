package com.es.phoneshop.web;

import com.es.phoneshop.model.Cart;
import com.es.phoneshop.services.CartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {

    @Mock
    CartService cartService;

    @Mock
    Cart cart;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    @InjectMocks
    CartPageServlet cartPageServlet;


    private static final Long PRODUCT_ID_WITH_ERROR = 2L;
    private final String PATH = "/WEB-INF/pages/cart.jsp";
    private final String[] productId = {"1", "2", "3"};
    private final String[] quantity = {"1", "2", "3"};
    private final String[] quantityWithErrors = {"1", "asdf", "3"};

    @Before
    public void setup() {
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getRequestDispatcher(PATH)).thenReturn(requestDispatcher);
        when(request.getParameterValues("productId")).thenReturn(productId);
        when(request.getParameterValues("quantity")).thenReturn(quantity);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        cartPageServlet.doGet(request, response);
        verify(request).setAttribute("cart", cart);
        verify(request).getRequestDispatcher(PATH);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDoPost() throws ServletException, IOException {
        Map<Long, String> errors = cartPageServlet.updateProductQuantity(request);
        assertNotNull(errors);
        assertTrue(errors.isEmpty());
    }

    @Test()
    public void testDoPostWithError() throws ServletException, IOException {
        when(request.getParameterValues("quantity")).thenReturn(quantityWithErrors);
        Map<Long, String> errors = cartPageServlet.updateProductQuantity(request);
        String errorsMessage = errors.get(PRODUCT_ID_WITH_ERROR);
        System.out.println(errorsMessage);
        assertNotNull(errorsMessage);
    }
}
