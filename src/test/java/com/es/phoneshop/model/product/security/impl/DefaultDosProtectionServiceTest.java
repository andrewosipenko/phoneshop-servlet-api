package com.es.phoneshop.model.product.security.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultDosProtectionServiceTest {

    private DefaultDosProtectionService defaultDosProtectionService;
    private HttpServletRequest requestMock;


    @Before
    public void init() {
        defaultDosProtectionService = DefaultDosProtectionService.getInstance();
        requestMock = Mockito.mock(HttpServletRequest.class);
        when(requestMock.getRemoteAddr()).thenReturn("Some address");
    }

    @Test
    public void testAddingProductToCart() {
        defaultDosProtectionService.isAllowed(requestMock.getRemoteAddr());
        Assert.assertTrue(defaultDosProtectionService.getRequestsMap().containsKey(requestMock.getRemoteAddr()));
    }
}
