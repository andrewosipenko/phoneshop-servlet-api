package com.es.phoneshop.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AbstractServletTest {
    @Mock
    private HttpServletRequest request;
    private AbstractServlet servlet = new AbstractServlet();
    private Locale locale = new Locale("RUS");
    private static final String PRODUCT_ID_FROM_URL = "/1";
    private static final String VALID_QUANTITY = "2";
    private static final String INVALID_QUANTITY = "hgh3hjbh";

    @Before
    public void setup() throws ServletException {
        servlet.init();
    }

    @Test
    public void testValidateQuantityInput() throws ParseException {
        when(request.getLocale()).thenReturn(locale);

        int quantity = servlet.validateQuantityInput(request, VALID_QUANTITY);

        int expectedQuantity = 2;
        assertEquals(expectedQuantity, quantity);
    }

    @Test(expected = NumberFormatException.class)
    public void testExceptionForProductQuantity() throws ParseException {
        when(request.getLocale()).thenReturn(locale);

        servlet.validateQuantityInput(request, INVALID_QUANTITY);
    }

    @Test
    public void testGetProductIdFromUrl() {
        when(request.getPathInfo()).thenReturn(PRODUCT_ID_FROM_URL);

        String productId = request.getPathInfo();

        assertEquals(PRODUCT_ID_FROM_URL, productId);
    }
}
