package com.es.phoneshop.model.product.history;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.Product;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpHistoryServiceTest {
    private HttpHistoryService historyService;

    @Mock
    HttpServletRequest request;
    @Mock
    HttpSession session;

    @Before
    public void setup() {
        ArrayListProductDao.getInstance().setProducts(new ArrayList<>());
        when(request.getSession()).thenReturn(session);
        historyService = HttpHistoryService.getInstance();
    }

    @Test
    public void addHistoryToSessionTest() throws ProductNotFoundException {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setStock(2);
        when(session.getAttribute(HttpHistoryService.HTTP_SESSION_HISTORY_KEY)).thenReturn(null);
        historyService.update(request, productId);
        verify(session).setAttribute(eq(HttpHistoryService.HTTP_SESSION_HISTORY_KEY), any(History.class));
    }

}
