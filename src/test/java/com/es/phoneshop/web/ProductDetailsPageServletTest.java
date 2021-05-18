package com.es.phoneshop.web;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.model.product.dao.TestableSingletonProductDao;
import com.es.phoneshop.web.servlets.ProductDetailsPageServlet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
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
    @Mock
    private HttpSession httpSession;

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();
    //don't know how to test correctly without this dependency
    private TestableSingletonProductDao<List<Product>> productDao = ArrayListProductDao.getInstance();

    private final Product exampleProduct = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

    @Before
    public void setup() throws ServletException {

        servlet.init(servletConfig);
        productDao.set(List.of(exampleProduct));
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(httpSession);
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

    @Test
    public void testDoPostWithNotANumberQuantity() throws IOException {
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getParameter("quantity")).thenReturn("first");
        when(request.getLocale()).thenReturn(Locale.ENGLISH);

        servlet.doPost(request,response);

        verify(response).sendRedirect(request.getContextPath() + "/products/" + "1" + "?error=Not a number");
    }

    @Test
    public void testDoPostWithNotEnoughStock() throws IOException {
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getParameter("quantity")).thenReturn(exampleProduct.getStock()+1+"");
        when(request.getLocale()).thenReturn(Locale.ENGLISH);

        servlet.doPost(request,response);

        verify(response).sendRedirect(request.getContextPath() + "/products/" + 1 + "?error=Not enough stock");
    }

    @Test
    public void testValidDoPost() throws IOException {
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getParameter("quantity")).thenReturn(exampleProduct.getStock()-1+"");
        when(request.getLocale()).thenReturn(Locale.ENGLISH);

        servlet.doPost(request,response);

        verify(response).sendRedirect(request.getContextPath() + "/products/" + 1 + "?message=Added to cart successfully");
    }
}
