package com.es.phoneshop.service;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDaoImpl;
import com.es.phoneshop.model.product.SortOrder;
import com.es.phoneshop.model.product.SortField;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private ProductDaoImpl productDao;
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
        return productDao.getProduct(id)
                .map(Optional::of)
                .orElseThrow(() -> new ProductNotFoundException("Invalid id while getting product in service"));
    }

    @Override
    public List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
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

    public ProductDaoImpl getProductDao() {
        return productDao;
    }

    public void setProductDao(ProductDaoImpl productDao) {
        this.productDao = productDao;
    }
}
