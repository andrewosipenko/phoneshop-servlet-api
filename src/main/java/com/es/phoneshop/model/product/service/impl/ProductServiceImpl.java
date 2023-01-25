package com.es.phoneshop.model.product.service.impl;

import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.model.product.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.LinkedHashSet;
import java.util.Set;

public class ProductServiceImpl implements ProductService {
    private static volatile ProductServiceImpl instance;
    private Set<Product> recentProducts;

    public static ProductServiceImpl getInstance() {
        ProductServiceImpl localInstance = instance;
        if (localInstance == null) {
            synchronized (CartServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null)
                    instance = localInstance = new ProductServiceImpl();
            }
        }
        return localInstance;
    }

    private ProductServiceImpl() {

    }

    @Override
    public Long parseProductIdFromRequestWithHistory(HttpServletRequest request) throws NumberFormatException {
        return Long.parseLong(request.getPathInfo().substring(1, request.getPathInfo().lastIndexOf('/')));
    }

    @Override
    public Long parseProductIdFromRequestWithoutHistory(HttpServletRequest request) throws NumberFormatException {
        return Long.parseLong(request.getPathInfo().substring(1));
    }

    @Override
    public void includeProductInRecentProducts(Product product, HttpServletRequest request) {
        if (recentProducts == null) {
            recentProducts = new LinkedHashSet<>();
        }
        if (recentProducts.size() > 2 && !recentProducts.contains(product)) {
            Product productToRemove = recentProducts.iterator().next();
            recentProducts.remove(productToRemove);
        }
        recentProducts.add(product);
        setRecentProductsInSession(request, recentProducts);
    }

    @Override
    public void setRecentProductsInSession(HttpServletRequest request, Set<Product> products) {
        HttpSession currentSession = request.getSession();
        currentSession.setAttribute("recentProducts", products);
    }

    @Override
    public Long parseProductIdFromDeleteOrAddRequest(HttpServletRequest request) throws NumberFormatException {
        return Long.parseLong(request.getPathInfo().substring(1));
    }
    @Override
    public int parseQuantity(String quantity, HttpServletRequest request) throws ParseException {
        int result;
        if (!quantity.matches("^\\d+([\\.\\,]\\d+)?$")) {
            throw new ParseException("Not a number!", 0);
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance(request.getLocale());
        result = numberFormat.parse(quantity).intValue();

        return result;
    }
}
