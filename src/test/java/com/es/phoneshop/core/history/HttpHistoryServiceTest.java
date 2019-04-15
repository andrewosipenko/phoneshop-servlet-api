package com.es.phoneshop.core.history;

import com.es.phoneshop.core.dao.product.ArrayListProductDao;
import com.es.phoneshop.core.dao.product.Product;
import com.es.phoneshop.core.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpHistoryServiceTest {
    @Mock
    HttpSession session;
    private HttpSessionHistoryService historyService;

    @Before
    public void setup() {
        ArrayListProductDao.getInstance().setProducts(new ArrayList<>());
        historyService = HttpSessionHistoryService.getInstance();
    }

    @Test
    public void addHistoryToSessionTest() throws ProductNotFoundException {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setStock(2);
        when(session.getAttribute(HttpSessionHistoryService.HTTP_SESSION_HISTORY_KEY)).thenReturn(null);
        historyService.update(session, productId);
        verify(session).setAttribute(eq(HttpSessionHistoryService.HTTP_SESSION_HISTORY_KEY), any(List.class));
    }

}
