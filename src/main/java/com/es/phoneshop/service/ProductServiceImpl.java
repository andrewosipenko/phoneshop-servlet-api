package com.es.phoneshop.service;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.ProductDaoImpl;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private static ProductServiceImpl instance;
    private ProductDao productDao;

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
    public Product getProduct(long id) {
        return productDao.getProduct(id).orElseThrow(() ->
                        new ProductNotFoundException("Invalid id " + id + " while getting product in service"));
    }

    @Override
    public List<Product> findProducts() {
        return productDao.findProducts();
    }

    @Override
    public List<Product> findProducts(String query) {
        return productDao.findProducts(query);
    }

    @Override
    public List<Product> findProducts(String query, String sortField, String sortOrder) {
        return productDao.findProducts(query, sortField, sortOrder);
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

    public void setProductDao(ProductDaoImpl productDao) {
        this.productDao = productDao;
    }
}
