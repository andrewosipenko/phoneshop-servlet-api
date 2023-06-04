package com.es.phoneshop.listener;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.any;

public class RecentlyViewedProductsListenerTest {

    @Mock
    private HttpSessionEvent event;

    @Mock
    private HttpSession session;

    private RecentlyViewedProductsListener listener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        listener = new RecentlyViewedProductsListener();
    }

    @Test
    public void givenValidSession_whenSessionCreated_thenSetValidAttribute() {
        when(event.getSession()).thenReturn(session);

        listener.sessionCreated(event);

        verify(session).setAttribute(eq("recentlyViewedProducts"), any(List.class));
    }
}
