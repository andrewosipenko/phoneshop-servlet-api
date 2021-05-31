package com.es.phoneshop.domain.common.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SortingOrderTest {

    @Test
    public void testFromString() {
        assertEquals(SortingOrder.DESC, SortingOrder.fromString("desc"));
        assertEquals(SortingOrder.ASC, SortingOrder.fromString("asc"));
        assertEquals(SortingOrder.DESC, SortingOrder.fromString("DESC"));
        assertEquals(SortingOrder.ASC, SortingOrder.fromString("ASC"));
        assertEquals(SortingOrder.DESC, SortingOrder.fromString("DEsC"));
        assertEquals(SortingOrder.ASC, SortingOrder.fromString("aSC"));
    }
    @Test
    public void testFromStringWrongStr() {
        assertNull(SortingOrder.fromString("qwerty"));
        assertNull(SortingOrder.fromString("1234"));
        assertNull(SortingOrder.fromString(""));
        assertNull(SortingOrder.fromString(null));
    }
}