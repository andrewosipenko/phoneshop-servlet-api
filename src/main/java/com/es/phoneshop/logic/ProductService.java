package com.es.phoneshop.logic;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.es.phoneshop.projectConstants.Constants.UP_PRICE;
import static com.es.phoneshop.projectConstants.Constants.DOWN_PRICE;
import static com.es.phoneshop.projectConstants.Constants.UP_DESCRIPTION;
import static com.es.phoneshop.projectConstants.Constants.DOWN_DESCRIPTION;
import static com.es.phoneshop.projectConstants.Constants.SEARCH_LINE;
import static com.es.phoneshop.projectConstants.Constants.SEARCH_LINE_ATTRIBUTE;
import static com.es.phoneshop.projectConstants.Constants.SORTING_PARAMETR;

public class ProductService {
    private static ProductService object;
    private static ProductDao product = ArrayListProductDao.getInstance();

    private ProductService(){}

    public static ProductService getInstance(){
        if(object == null){
            synchronized(ProductService.class){
                if(object == null){
                    object = new ProductService();
                }
            }
        }
        return object;
    }

    public List<Product> findProducts(HttpServletRequest request){
        List<Product> productList = product.findProducts();
        String searchLine = request.getParameter(SEARCH_LINE);
        if (searchLine != null && !searchLine.isEmpty()) {
            request.setAttribute(SEARCH_LINE_ATTRIBUTE, searchLine);
            productList = searchLine(searchLine, productList);
        } else {
            request.setAttribute(SEARCH_LINE_ATTRIBUTE, "");
        }
        String sortingParameter = request.getParameter(SORTING_PARAMETR);
        return sortingByParam(sortingParameter, productList);
    }

    private List<Product> searchLine(String searchLine, List<Product> productList) {
        String[] searchWords = searchLine.split("\\s+");
        productList = productList.stream().filter(x -> {
            for (String str : searchWords) {
                if (x.getDescription().toUpperCase().contains(str.toUpperCase())) {
                    return true;
                }
            }
            return false;
        }).sorted((x, y) -> {
            int containCounterX = 0;
            int containCounterY = 0;
            for (String str : searchWords) {
                if (x.getDescription().toUpperCase().contains(str.toUpperCase())) {
                    containCounterX++;
                }
                if (y.getDescription().toUpperCase().contains(str.toUpperCase())) {
                    containCounterY++;
                }
            }
            return containCounterY - containCounterX;
        }).collect(Collectors.toList());

        return productList;
    }

    private List<Product> sortingByParam(String sortingParameter, List<Product> productList) {
        if (sortingParameter != null) {
            switch (sortingParameter) {
                case UP_DESCRIPTION:
                    productList.sort(Comparator.comparing(Product::getDescription));
                case DOWN_DESCRIPTION:
                    productList.sort(Comparator.comparing(Product::getDescription).reversed());
                case UP_PRICE:
                    productList.sort(Comparator.comparing(Product::getPrice));
                case DOWN_PRICE:
                    productList.sort(Comparator.comparing(Product::getPrice).reversed());
                default:
                    return productList;
            }
        }
        return productList;
    }






}

