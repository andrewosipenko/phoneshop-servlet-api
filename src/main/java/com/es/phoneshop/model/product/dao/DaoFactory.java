package com.es.phoneshop.model.product.dao;

public final class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();

    private final ProductDao productDaoImpl = new ArrayListProductDao();

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        return instance;
    }

    public ProductDao getProductDaoImpl() {
        return productDaoImpl;
    }
}
