package com.es.phoneshop.web.helper;

import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;

import static com.es.phoneshop.web.helper.ProductDetailsErrorHandler.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsErrorHandlerTest {
    @Mock
    HttpServletRequest request;

    private ProductDetailsErrorHandler productDetailsErrorHandler;

    @Before
    public void setup() {
        productDetailsErrorHandler = new ProductDetailsErrorHandler();
    }

    @Test
    public void handleProductAddedTest() {
        when(request.getParameter(PRODUCT_ADDED_KEY)).thenReturn("ok");
        productDetailsErrorHandler.handle(request);
        verify(request).setAttribute(PRODUCT_ADDED_KEY, SUCCESSFULLY_ADDED_MESSAGE);
    }

    @Test
    public void handleParseErrorTest() {
        when(request.getParameter(PRODUCT_ADDED_KEY)).thenReturn(null);
        when(request.getParameter(ERROR_KEY)).thenReturn(Error.PARSE_ERROR.getErrorCode());
        productDetailsErrorHandler.handle(request);
        verify(request).setAttribute(ERROR, PARSE_ERROR_MESSAGE);
    }

    @Test
    public void handleOutOfStockError() {
        when(request.getParameter(PRODUCT_ADDED_KEY)).thenReturn(null);
        when(request.getParameter(ERROR_KEY)).thenReturn(Error.OUT_OF_STOCK.getErrorCode());
        productDetailsErrorHandler.handle(request);
        verify(request).setAttribute(ERROR, OUT_OF_STOCK_ERROR_MESSAGE);
    }

    @Test
    public void handleUnknownError() {
        when(request.getParameter(PRODUCT_ADDED_KEY)).thenReturn(null);
        when(request.getParameter(ERROR_KEY)).thenReturn(Error.UNKNOWN.getErrorCode());
        productDetailsErrorHandler.handle(request);
        verify(request).setAttribute(ERROR, UNKNOWN_ERROR_MESSAGE);
    }
}
