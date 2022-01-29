package com.es.phoneshop.web;

import com.es.phoneshop.dao.SortField;
import com.es.phoneshop.dao.SortOrder;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = new ArrayListProductDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        SortField sortField = Optional.ofNullable(request.getParameter("sort"))
                .map(SortField::valueOf)
                .orElse(null);
        SortOrder sortOrder = Optional.ofNullable(request.getParameter("order"))
                .map(SortOrder::valueOf)
                .orElse(null);
        request.setAttribute("products", productDao.findProducts(query, sortField, sortOrder));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
