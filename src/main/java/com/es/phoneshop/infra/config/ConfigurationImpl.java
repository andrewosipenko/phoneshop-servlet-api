package com.es.phoneshop.infra.config;

import com.es.phoneshop.domain.product.persistence.ArrayListProductDao;
import com.es.phoneshop.domain.product.persistence.ProductDao;
import com.es.phoneshop.utils.LongIdGenerator;
import com.es.phoneshop.utils.LongIdGeneratorImpl;

import java.time.Clock;

public class ConfigurationImpl implements Configuration {

    private static ConfigurationImpl instance;

    private ProductDao productDao;

    private LongIdGenerator longIdGenerator;

    private ConfigurationImpl() { }

    public static synchronized ConfigurationImpl getInstance() {
        if (instance == null){
            instance = new ConfigurationImpl();
        }
        return instance;
    }

    @Override
    public synchronized ProductDao getProductDao() {
        if (productDao == null){
            productDao = new ArrayListProductDao(getLongIdGenerator(), Clock.systemUTC());
        }
        return productDao;
    }

    @Override
    public synchronized LongIdGenerator getLongIdGenerator() {
        if (longIdGenerator == null){
            longIdGenerator = new LongIdGeneratorImpl(0L);
        }
        return longIdGenerator;
    }
}
