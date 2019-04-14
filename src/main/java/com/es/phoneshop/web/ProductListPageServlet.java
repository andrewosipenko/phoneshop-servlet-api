package com.es.phoneshop.web;

import com.es.phoneshop.model.product.dao.product.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.product.ProductDao;
import com.es.phoneshop.model.product.enums.SortBy;
import com.es.phoneshop.model.product.history.HistoryService;
import com.es.phoneshop.model.product.history.HttpSessionHistoryService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    protected static final String QUERY = "query";
    protected static final String ORDER = "order";
    protected static final String SORT = "sort";
    protected static final String PRODUCTS = "products";

    private final ProductDao productDao = ArrayListProductDao.getInstance();
    private HistoryService sessionHistoryService;

    @Override
    public void init(ServletConfig config) {
        sessionHistoryService = HttpSessionHistoryService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter(QUERY);
        boolean ascending = true;
        if (request.getParameter(ORDER) != null) {
            ascending = request.getParameter(ORDER).equals("asc");
        }
        SortBy field = null;
        if (request.getParameter(SORT) != null) {
            field = SortBy.valueOf(request.getParameter(SORT).toUpperCase());
        }
        sessionHistoryService.update(request.getSession(), null);
        request.setAttribute(PRODUCTS, productDao.findProducts(query, field, ascending));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
