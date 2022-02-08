package com.es.phoneshop.model.product.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class QuantityValidatorTest extends TestCase {
    private String quantity;
    private boolean expectedResult;

    public QuantityValidatorTest(String quantity, Boolean expectedResult) {
        this.quantity = quantity;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(new Object[][]{
                {"123", true},
                {"0", true},
                {"fdsgfgd", false}
        });
    }

    @Test
    public void testValidatorQuantityNotNumber() {
        assertEquals(expectedResult,
                QuantityValidator.isNumber(quantity));
    }
}