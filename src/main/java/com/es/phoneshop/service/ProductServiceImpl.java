package com.es.phoneshop.service;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductDaoImpl;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;
    private static ProductServiceImpl instance;

    private ProductServiceImpl() {
        productDao = ProductDaoImpl.getInstance();
    }

    public static synchronized ProductServiceImpl getInstance() {
        if (instance == null) {
            instance = new ProductServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<Product> getProduct(long id) {
        Optional<Product> product = productDao.getProduct(id);
        if (product.isEmpty()){
            throw new IllegalArgumentException("Invalid id while getting product in service");
        }
        return product;
    }

    @Override
    public List<Product> findProducts() {
        return productDao.findProducts();
    }

    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public void delete(long id) throws ProductNotFoundException {
        productDao.delete(id);
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
