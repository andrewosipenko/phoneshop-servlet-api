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
import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDemodataServletContextListenerTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    protected ServletConfig config;

    private final ProductDemoDataServletContextListener servlet = new ProductDemoDataServletContextListener();

//    @Before
//    public void setup() throws ServletException {
//        servlet.init(config);
//        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
//    }

    @Test
    public void testDoGet() throws ServletException, IOException {
      //verify(servlet.)
    }

}