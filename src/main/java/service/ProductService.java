package service;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.ProductDao;

public interface ProductService {
    void init() throws ProductNotFoundException;
    ProductDao getProductDao();
}
