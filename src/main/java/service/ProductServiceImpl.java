package service;

import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductDaoImpl;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;
    private static ProductServiceImpl instance;

    public static synchronized ProductServiceImpl getInstance() {
        if (instance == null) {
            instance = new ProductServiceImpl();
            instance.init();
        }
        return instance;
    }

    @Override
    public void init() {
        productDao = ProductDaoImpl.getInstance();
    }

    @Override
    public ProductDao getProductDao() {
        return productDao;
    }
}
