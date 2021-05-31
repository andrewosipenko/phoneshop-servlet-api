package com.es.phoneshop.infra.config;

import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigurationImplTest{

    private static Configuration configuration;

    @BeforeClass
    public static void setupAll(){
        configuration = ConfigurationImpl.getInstance();
    }

    @Test
    public void testGetInstance(){
        assertEquals(configuration, ConfigurationImpl.getInstance());
    }
    @Test
    public void testGetProductDao(){
        assertEquals(configuration.getProductDao(), configuration.getProductDao());
    }
    @Test
    public void testGetLongIdGenerator(){
        assertEquals(configuration.getLongIdGenerator(), configuration.getLongIdGenerator());
    }

}