package com.es.phoneshop.web;

import com.es.phoneshop.core.cart.Cart;
import com.es.phoneshop.core.cart.HttpSessionCartService;
import com.es.phoneshop.web.listeners.ProductDemodataServletContextListener;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    private static final ProductDemodataServletContextListener productDemodataServletContextListener =
            new ProductDemodataServletContextListener();
    @Mock
    private static ServletContextEvent servletContextEvent;
    private final CartPageServlet cartPageServlet = new CartPageServlet();
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private HttpSessionCartService httpSessionCartService;
    @Mock
    private Cart cart;

    @BeforeClass
    public static void beforeAll() {
        productDemodataServletContextListener.contextInitialized(servletContextEvent);
    }

    @Before
    public void start() {
        cartPageServlet.init(servletConfig);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameterValues("id")).thenReturn(new String[]{"1"});
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        cartPageServlet.doGet(request, response);
        verify(request).getRequestDispatcher("/WEB-INF/pages/cart.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostOk() throws ServletException, IOException {
        when(request.getParameterValues("quantity")).thenReturn(new String[]{"1"});
        cartPageServlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostBad() throws ServletException, IOException {
        when(httpSessionCartService.getCart(request)).thenReturn(cart);
        when(request.getParameterValues("quantity")).thenReturn(new String[]{"-1"});
        cartPageServlet.setCartService(httpSessionCartService);

        cartPageServlet.doPost(request, response);
        verify(request).setAttribute(eq("errors"), any(String[].class));
    }
}
