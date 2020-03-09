package com.es.phoneshop.web;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.enums.SortField;
import com.es.phoneshop.model.enums.SortOrder;
import com.es.phoneshop.service.RecentlyViewedService;
import com.es.phoneshop.service.impl.DefaultRecentlyViewedService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;
    private RecentlyViewedService recentlyViewedService;

    @Override
    public void init(ServletConfig config) {
        productDao = ArrayListProductDao.getInstance();
        recentlyViewedService = DefaultRecentlyViewedService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        SortField field = getSortField(request);
        SortOrder order = getSortOrder(request);

        request.setAttribute("recentlyViewed", recentlyViewedService.getProducts(request));
        request.setAttribute("products", productDao.findProducts(query, field, order));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    private SortField getSortField(HttpServletRequest request) {
        String field = request.getParameter("sortField");
        if (field == null) {
            return null;
        }
        return SortField.valueOf(field.toUpperCase());
    }

    private SortOrder getSortOrder(HttpServletRequest request) {
        String order = request.getParameter("sortOrder");
        if (order == null) {
            return null;
        }
        return SortOrder.valueOf(order.toUpperCase());
    }
}
