package com.es.phoneshop.utils;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LongIdGeneratorImplTest {

    private LongIdGenerator idGenerator;

    @Before
    public void setup() {
        idGenerator = new LongIdGeneratorImpl(0L);
    }

    @Test
    public void testGetId() {
        assertEquals(0L, (long) idGenerator.getId());
    }
}