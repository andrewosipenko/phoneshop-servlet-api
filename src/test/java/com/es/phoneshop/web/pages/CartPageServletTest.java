package com.es.phoneshop.web.pages;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
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
import java.math.BigDecimal;
import java.util.Currency;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig config;

    @Mock
    private HttpSession session;

    private CartPageServlet servlet = new CartPageServlet();

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        String[] strings = new String[1];
        strings[0] = "1";

        when(request.getParameterValues(anyString())).thenReturn(strings);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);

        ProductDao productDao = ArrayListProductDao.getInstance();
        Currency currency = Currency.getInstance("USD");
        Product product = new Product(1L, "test", "HTC EVO Shift 4G", new BigDecimal(320), currency, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg");
        productDao.save(product);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher(eq("/WEB-INF/pages/cart.jsp"));
        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute(eq("cart"), any());
        verify(request).setAttribute(eq("errors"), any());
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostNegativeQuantity() throws IOException, ServletException {
        String[] stringsIds = new String[1];
        stringsIds[0] = "1";

        String[] stringsQuantities = new String[1];
        stringsQuantities[0] = "-1";

        when(request.getParameterValues("productId")).thenReturn(stringsIds);
        when(request.getParameterValues("quantity")).thenReturn(stringsQuantities);

        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostNonNumberQuantity() throws IOException, ServletException {
        String[] stringsIds = new String[1];
        stringsIds[0] = "1";

        String[] stringsQuantities = new String[1];
        stringsQuantities[0] = "not number";

        when(request.getParameterValues("productId")).thenReturn(stringsIds);
        when(request.getParameterValues("quantity")).thenReturn(stringsQuantities);

        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }
}