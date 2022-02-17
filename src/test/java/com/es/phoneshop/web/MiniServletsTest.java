package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.recentView.HttpSessionRecentViewService;
import com.es.phoneshop.model.recentView.RecentView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MiniServletsTest {
    private Lock lock = new ReentrantLock();

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private HttpSession httpSession;

    @Before
    public void setup() throws ServletException {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGetMiniCart() throws ServletException, IOException {
        MiniCartServlet servlet = new MiniCartServlet();
        servlet.init(servletConfig);
        Cart cart = new Cart();
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(HttpSessionCartService.class.getName() + ".lock")).thenReturn(lock);
        when(httpSession.getAttribute(HttpSessionCartService.class.getName() + ".cart")).thenReturn(cart);
        servlet.doGet(request, response);
        verify(requestDispatcher).include(request, response);
    }

    @Test
    public void testDoGetRecentViewServlet() throws ServletException, IOException {
        RecentViewServlet servlet = new RecentViewServlet();
        servlet.init(servletConfig);
        RecentView recentView = new RecentView(lock);
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(HttpSessionRecentViewService.class.getName() + ".lock")).thenReturn(lock);
        when(httpSession.getAttribute(HttpSessionRecentViewService.class.getName() + ".recentView")).thenReturn(recentView);
        servlet.doGet(request, response);
        verify(requestDispatcher).include(request, response);
    }
}
