package com.es.phoneshop.domain.product.persistence;

import com.es.phoneshop.utils.LongIdGeneratorImpl;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest extends TestCase {

    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao(new LongIdGeneratorImpl());
    }


    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.getAllAvailable().isEmpty());
    }

}