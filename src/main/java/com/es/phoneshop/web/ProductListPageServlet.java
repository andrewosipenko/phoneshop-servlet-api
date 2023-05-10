package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;

import com.es.phoneshop.enums.SortingField;
import com.es.phoneshop.enums.SortingType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;
    private static final String DESCRIPTION = "description";
    private static final String SORTING = "sorting";
    private static final String SORTING_TYPE = "type";
    private static final String PRODUCTS = "products";

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description = request.getParameter(DESCRIPTION);
        String sortingField = request.getParameter(SORTING);
        String sortingType = request.getParameter(SORTING_TYPE);
        request.setAttribute(PRODUCTS, productDao.findProducts(description,
                Optional.ofNullable(sortingField).map(SortingField::valueOf).orElse(null),
                Optional.ofNullable(sortingType).map(SortingType::valueOf).orElse(null)));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
