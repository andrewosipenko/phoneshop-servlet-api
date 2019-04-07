package com.es.phoneshop.web;

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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CartItemDeleteServletTest {
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

    private final CartItemDeleteServlet cartItemDeleteServlet = new CartItemDeleteServlet();

    @Before
    public void start() {
        cartItemDeleteServlet.init(servletConfig);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testDoPost() throws IOException {
        when(request.getPathInfo()).thenReturn("/1");
        cartItemDeleteServlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/cart.jsp")).thenReturn(requestDispatcher);
        cartItemDeleteServlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }
}
