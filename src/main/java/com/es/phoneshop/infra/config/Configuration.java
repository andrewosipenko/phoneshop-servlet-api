package com.es.phoneshop.infra.config;

import com.es.phoneshop.domain.product.persistence.ProductDao;
import com.es.phoneshop.utils.LongIdGenerator;

public interface Configuration {
    ProductDao getProductDao();

    LongIdGenerator getLongIdGenerator();

}
