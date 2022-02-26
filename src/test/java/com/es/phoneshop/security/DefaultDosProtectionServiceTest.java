package com.es.phoneshop.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DefaultDosProtectionServiceTest {


    private DosProtectionService dosProtectionService;

    @Before
    public void setup() {
        dosProtectionService = DefaultDosProtectionService.getInstance();
    }

    @Test
    public void isAllowedTest() {
        String ip = "192.168.1.1";
        for (int i = 0; i < 50; i++) {
            assertTrue(dosProtectionService.isAllowed(ip));
        }
        assertFalse(dosProtectionService.isAllowed(ip));
    }
}


