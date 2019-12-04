package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.recentlyViewed.RecentlyViewedProducts;
import com.es.phoneshop.model.recentlyViewed.ViewedProductsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {

    @Mock
    HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private HttpSession httpSession;
    @Mock
    private CartService cartService;
    @Mock
    private ProductDao productDao;
    @Mock
    private ViewedProductsService viewedProductsService;

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setUp() {
        servlet.setCartService(cartService);
        servlet.setProductDao(productDao);
        servlet.setViewedProductsService(viewedProductsService);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        httpSession = Mockito.mock(HttpSession.class);
        when(request.getSession()).thenReturn(httpSession);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/11");

        Cart cart = new Cart();
        RecentlyViewedProducts viewedProducts = new RecentlyViewedProducts();
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg");

        when(productDao.getProduct(11L)).thenReturn(product);
        when(cartService.getCart(request)).thenReturn(cart);
        when(viewedProductsService.getViewedProducts(request)).thenReturn(viewedProducts);

        servlet.doGet(request, response);

        verify(httpSession).setAttribute("viewedProducts", viewedProducts);
        verify(request).setAttribute("product", product);
        verify(request).setAttribute("cart", cart);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostSuccessfully() throws ServletException, IOException {
        when(request.getLocale()).thenReturn(new Locale("en"));
        when(request.getRequestURI()).thenReturn("/11");
        when(request.getParameter("quantity")).thenReturn("2");

        Cart cart = new Cart();
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg");

        when(productDao.getProduct(11L)).thenReturn(product);
        when(cartService.getCart(request)).thenReturn(cart);

        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostWithNotANumberError() throws ServletException, IOException {
        when(request.getLocale()).thenReturn(new Locale("en"));
        when(request.getRequestURI()).thenReturn("/11");
        when(request.getParameter("quantity")).thenReturn("eee");

        Cart cart = new Cart();
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg");

        when(productDao.getProduct(11L)).thenReturn(product);
        when(cartService.getCart(request)).thenReturn(cart);

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Not a number");
        verify(request).setAttribute("product", product);
        verify(request).setAttribute("cart", cart);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostWithOutOfStockError() throws ServletException, IOException {
        CartService cartService = HttpSessionCartService.getInstance();
        servlet.setCartService(cartService);

        when(request.getLocale()).thenReturn(new Locale("en"));
        when(request.getRequestURI()).thenReturn("/11");
        when(request.getParameter("quantity")).thenReturn("999");

        Currency usd = Currency.getInstance("USD");
        Product product = new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg");

        when(productDao.getProduct(11L)).thenReturn(product);

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Not enough stock. Available " + product.getStock());
        verify(request).setAttribute("product", product);
        verify(requestDispatcher).forward(request, response);
    }
}
