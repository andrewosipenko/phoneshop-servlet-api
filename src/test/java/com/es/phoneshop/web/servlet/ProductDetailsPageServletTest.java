package com.es.phoneshop.web.servlet;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.recentlyViewedProducts.RecentlyViewedProducts;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.RecentlyViewedProductsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ArrayListProductDao productDao;
    @Mock
    private CartService cartService;
    @Mock
    private RecentlyViewedProductsService recentlyViewedProductsService;
    @InjectMocks
    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void doGetSetup() {
        Long id = 1L;
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn("/" + id);

        Currency usd = Currency.getInstance("USD");
        Product product = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        RecentlyViewedProducts recentlyViewedProducts = new RecentlyViewedProducts();
        recentlyViewedProducts.getProducts().push(product);

        when(cartService.getCart(request)).thenReturn(new Cart());
        when(recentlyViewedProductsService.getProducts(request)).thenReturn(recentlyViewedProducts);
        when(productDao.findById(id)).thenReturn(product);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).getPathInfo();

        verify(requestDispatcher).forward(request, response);

        Product product = productDao.findById(1L);
        RecentlyViewedProducts recentlyViewedProducts = new RecentlyViewedProducts();
        recentlyViewedProducts.getProducts().push(product);

        verify(request).setAttribute("product", product);
        verify(request).setAttribute("recently_viewed", recentlyViewedProducts);
    }

    @Test
    public void testDoPostDecimalQuantity() throws ServletException, IOException {
        when(request.getLocale()).thenReturn(Locale.ROOT);
        when(request.getParameter("quantity")).thenReturn("1.5");

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Number should be integer");
    }

    @Test
    public void testDoPostNonNumericalQuantity() throws ServletException, IOException {
        when(request.getLocale()).thenReturn(Locale.ROOT);
        when(request.getParameter("quantity")).thenReturn("Not a number");

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Not a number");
    }

    @Test
    public void testDoPostNegativeQuantity() throws ServletException, IOException {
        when(request.getLocale()).thenReturn(Locale.ROOT);
        when(request.getParameter("quantity")).thenReturn("-1");

        servlet.doPost(request, response);

        verify(request).setAttribute("error", "Number should be greater than zero");
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getLocale()).thenReturn(Locale.ROOT);
        when(request.getParameter("quantity")).thenReturn("100");

        servlet.doPost(request, response);

        verify(request).setAttribute("message", "Product added to cart");
    }
}
