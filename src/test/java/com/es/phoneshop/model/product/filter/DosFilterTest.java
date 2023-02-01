package com.es.phoneshop.model.product.filter;

import com.es.phoneshop.web.filter.DosFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class DosFilterTest {
    @Mock
    private FilterConfig filterConfig;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    @Test
    public void testInit() throws ServletException, IOException {
        Mockito.when(request.getRemoteAddr()).thenReturn("some addr");
        DosFilter dosFilterTest = new DosFilter();
        dosFilterTest.init(filterConfig);
        dosFilterTest.doFilter(request, response, filterChain);
    }
}
