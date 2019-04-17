package com.es.phoneshop.web;

import com.es.phoneshop.core.dao.product.ArrayListProductDao;
import com.es.phoneshop.core.exceptions.ProductNotFoundException;
import com.es.phoneshop.web.helper.Error;
import com.es.phoneshop.web.listeners.ProductDemodataServletContextListener;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    private static final ProductDemodataServletContextListener productDemodataServletContextListener
            = new ProductDemodataServletContextListener();
    @Mock
    private static ServletContextEvent servletContextEvent;
    @Mock
    private static ServletContext servletContext;

    private final ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();
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

    @BeforeClass
    public static void start() {
        ArrayListProductDao.getInstance().setProducts(new ArrayList<>());
    }

    @Before
    public void setup() {
        servlet.init(servletConfig);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(httpSession);
        when(request.getRequestURI()).thenReturn("");
        productDemodataServletContextListener.contextInitialized(servletContextEvent);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        final Long CORRECT_ID = 1L;
        when(request.getPathInfo()).thenReturn("/" + CORRECT_ID);
        servlet.doGet(request, response);
        verify(request).setAttribute(ProductDetailsPageServlet.ID, CORRECT_ID);
        verify(request).setAttribute(
                ProductDetailsPageServlet.PRODUCT,
                ArrayListProductDao.getInstance().getProduct(CORRECT_ID)
        );
        String PATH = "/WEB-INF/pages/productDetails.jsp";
        verify(request).getRequestDispatcher(PATH);
        verify(requestDispatcher).forward(request, response);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testInvalidIdPath() throws ServletException, IOException {
        final Long INVALID_ID = Long.MAX_VALUE;
        when((request.getPathInfo())).thenReturn("/" + INVALID_ID);
        servlet.doGet(request, response);
        verify(request).setAttribute(ProductDetailsPageServlet.ID, INVALID_ID);
        String PATH = "/WEB-INF/pages/productNotFound.jsp";
        verify(request).getRequestDispatcher(PATH);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testParseError() throws ServletException, IOException {
        when(request.getParameter(ProductDetailsPageServlet.QUANTITY)).thenReturn("asd");
        when(request.getPathInfo()).thenReturn("/1");
        servlet.doPost(request, response);
        verify(request).setAttribute("error", Error.PARSE_ERROR.getErrorMessage());
    }

    @Test
    public void testOutOfStockError() throws ServletException, IOException {
        final long CORRECT_ID = 1L;
        when(request.getPathInfo()).thenReturn("/" + CORRECT_ID);
        when(request.getParameter(ProductDetailsPageServlet.QUANTITY)).thenReturn(String.valueOf(Integer.MAX_VALUE));
        servlet.doPost(request, response);
        verify(request).setAttribute("error", Error.OUT_OF_STOCK.getErrorMessage());
    }

    @Test
    public void testProductSuccessfullyAdded() throws ServletException, IOException {
        final long CORRECT_ID = 1L;
        when(request.getPathInfo()).thenReturn("/" + CORRECT_ID);
        when(request.getParameter(ProductDetailsPageServlet.QUANTITY)).thenReturn(String.valueOf(1));
        servlet.doPost(request, response);
        verify(response).sendRedirect(request.getRequestURI() + "?productAdded=ok");
    }
}
