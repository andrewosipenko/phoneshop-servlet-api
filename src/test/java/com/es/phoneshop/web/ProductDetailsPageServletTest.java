package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.TestableSingletonProductDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig servletConfig;


    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();
    //don't know how to test correctly without this dependency
    private TestableSingletonProductDao<List<Product>> productDao = ArrayListProductDao.getInstance();

    @Before
    public void setup() throws ServletException {

        servlet.init(servletConfig);
        productDao.set(List.of(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg")));
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/1");

        servlet.doGet(request, response);
        verify(request).getRequestDispatcher(eq("/WEB-INF/pages/productDetails.jsp"));
        verify(request).setAttribute(eq("product"), any());
    }


    @Test
    public void testDoGetWithPriceHistory() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/1/priceHistory");

        servlet.doGet(request, response);
        verify(request).getRequestDispatcher(eq("/WEB-INF/pages/priceHistoryPage.jsp"));
    }

    @Test(expected = NoSuchElementException.class)
    public void testDoGetWithNoResult() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/lol/priceHistory");
        servlet.doGet(request, response);
    }

}
