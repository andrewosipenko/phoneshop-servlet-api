package com.es.phoneshop.web.pages;

import com.es.phoneshop.web.pages.DeleteCartItemServlet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
public class DeleteCartItemServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletConfig config;

    @Mock
    private HttpSession session;

    private DeleteCartItemServlet servlet = new DeleteCartItemServlet();

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testDoPost() throws IOException {
        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }
}