package com.es.phoneshop.service;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.service.impl.ViewedProductsServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

public class DataLoader {
    private ProductDao productDao;
    private ViewedProductsService viewedProductsService;

    public DataLoader(){
        productDao = ArrayListProductDao.getInstance();
        viewedProductsService = ViewedProductsServiceImpl.getInstance();
    }

    public Integer loadQuantity(HttpServletRequest request, String quantityParameter) throws NumberFormatException{
        String quantityString = request.getParameter(quantityParameter);
        return Integer.parseUnsignedInt(quantityString);
    }

    public Integer loadQuantity(String quantityString) throws NumberFormatException{
        return Integer.parseUnsignedInt(quantityString);
    }

    public Product loadProductFromURI(HttpServletRequest request) throws NumberFormatException, NoSuchElementException{
        String uri = request.getRequestURI();
        int idIndex = uri.lastIndexOf("/");
        String stringId = uri.substring(idIndex + 1);
        Long id = Long.parseLong(stringId);
        return productDao.getProduct(id);
    }

    public Product loadProduct(String productId) throws NumberFormatException, NoSuchElementException{
        Long id = Long.parseLong(productId);
        return productDao.getProduct(id);
    }
}
