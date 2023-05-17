package com.es.phoneshop.listener;

import com.es.phoneshop.service.impl.CartServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.any;

public class CartListenerTest {

    @Mock
    private HttpSession session;

    @Mock
    private HttpSessionEvent event;

    private CartListener listener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        listener = new CartListener();
    }

    @Test
    public void givenValidSession_whenSessionCreated_thenSetValidAttribute() {
        when(event.getSession()).thenReturn(session);

        listener.sessionCreated(event);

        verify(session).setAttribute(eq(CartServiceImpl.class.getName() + ".cart"), any());
    }
}
