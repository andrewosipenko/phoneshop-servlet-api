package com.es.phoneshop.logic;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class ProductLogic{
    private static ProductDao product = ArrayListProductDao.getObject();

    public static List<Product> findProducts(HttpServletRequest request){
        List<Product> productList = product.findProducts();
        String searchLine = request.getParameter("searchLine");
        if (searchLine != null && !searchLine.isEmpty()) {
            request.setAttribute("searchLineAttrib", searchLine);
            productList = searchLine(searchLine, productList);
        } else {
            request.setAttribute("searchLineAttrib", "");
        }
        String sortingParameter = request.getParameter("sortingParameter");
        return sortingByParam(sortingParameter, productList);
    }

    private static List<Product> searchLine(String searchLine, List<Product> productList) {
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

    private static List<Product> sortingByParam(String sortingParameter, List<Product> productList) {
        if (sortingParameter != null) {
            switch (sortingParameter) {
                case "upDescription":
                    return productList.stream().sorted((x, y) -> x.getDescription().
                            compareTo(y.getDescription())).collect(Collectors.toList());
                case "downDescription":
                    return productList.stream().sorted((x, y) -> y.getDescription().
                            compareTo(x.getDescription())).collect(Collectors.toList());
                case "upPrice":
                    return productList.stream().sorted((x, y) -> x.getPrice().
                            compareTo(y.getPrice())).collect(Collectors.toList());
                case "downPrice":
                    return productList.stream().sorted((x, y) -> y.getPrice().
                            compareTo(x.getPrice())).collect(Collectors.toList());
                default:
                    return productList;
            }
        } else {
            return productList;
        }
    }

}

