package com.es.phoneshop.security;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DefaultDosProtectionServiceTest {
    private DosProtectionService dosProtectionService;

    @Before
    public void setup() {
        dosProtectionService = DefaultDosProtectionService.getInstance();
    }

    @Test
    public void testIsAllowedFalse() {
        for (int i = 0; i < 21; i++) {
            dosProtectionService.isAllowed("ip");
        }

        assertFalse(dosProtectionService.isAllowed("ip"));
    }

    @Test
    public void testIsAllowedTrue() throws InterruptedException {
        for (int i = 0; i < 21; i++) {
            dosProtectionService.isAllowed("ip");
        }

        Thread.sleep(60 * 1000);

        assertTrue(dosProtectionService.isAllowed("ip"));
    }
}