package com.es.phoneshop.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
    private DeleteCartItemServlet servlet = new DeleteCartItemServlet();
    private static final String PRODUCT_ID_FROM_URL = "/1";

    @Before
    public void setup() {
        servlet.init();
    }
    @Test
    public void doPost() throws IOException {
        when(request.getPathInfo()).thenReturn(PRODUCT_ID_FROM_URL);

        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }
}
