package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductListPageServlet extends HttpServlet {
    protected static final String QUERY = "query";
    protected static final String ORDER = "order";
    protected static final String SORT = "sort";
    protected static final String PRODUCTS = "products";

    private ProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter(QUERY);
        String order = request.getParameter(ORDER);
        String sortBy = request.getParameter(SORT);

        List<Product> products;
        if (query != null) {
            products = productDao.findProductsByDescription(query);
        } else {
            products = productDao.findProducts();
        }
        request.setAttribute(PRODUCTS, checkReadyToSort(products, sortBy, order));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    private List<Product> checkReadyToSort(List<Product> products, String sortBy, String order) {
        boolean readyToSort = order != null && sortBy != null;
        if (readyToSort && products.size() > 1) {
            boolean asc = order.equals("asc");
            switch (sortBy) {
                case "description":
                    products = products.stream()
                            .sorted(Comparator.comparing(Product::getDescription))
                            .collect(Collectors.toList());
                    break;
                case "price":
                    products = products.stream()
                            .sorted(Comparator.comparing(Product::getPrice))
                            .collect(Collectors.toList());
                    break;
            }

            if (!asc) Collections.reverse(products);
        }
        return products;
    }
}
