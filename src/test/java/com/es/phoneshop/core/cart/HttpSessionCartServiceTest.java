package com.es.phoneshop.core.cart;

import com.es.phoneshop.core.dao.product.ArrayListProductDao;
import com.es.phoneshop.core.dao.product.Product;
import com.es.phoneshop.core.exceptions.OutOfStockException;
import com.es.phoneshop.core.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    private final List<CartItem> items = new ArrayList<>();
    @Mock
    private Cart customerCart;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession httpSession;
    private HttpSessionCartService httpSessionCartService;

    @Before
    public void setup() {
        ArrayListProductDao.getInstance().setProducts(new ArrayList<>());
        httpSessionCartService = HttpSessionCartService.getInstance();
        when(customerCart.getCartItems()).thenReturn(items);
        when(request.getSession()).thenReturn(httpSession);
    }

    @Test
    public void testAdd() throws ProductNotFoundException, OutOfStockException {
        final long CORRECT_ID = 1L;
        final int QUANTITY = 1;
        Product product = new Product();
        product.setId(1L);
        product.setStock(2);
        ArrayListProductDao.getInstance().save(product);
        httpSessionCartService.add(request, customerCart, new CartItem(CORRECT_ID, QUANTITY));
        verify(customerCart, Mockito.atLeast(2)).getCartItems();
        verify(httpSession, Mockito.times(2)).setAttribute(anyString(), any(Cart.class));
    }
}
