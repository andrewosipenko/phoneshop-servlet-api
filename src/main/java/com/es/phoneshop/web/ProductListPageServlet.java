package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortType;
import com.es.phoneshop.model.recentView.HttpSessionRecentViewService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String sortField = request.getParameter("sort");
        String sortType = request.getParameter("order");
        if(sortField == null) {
            sortField = "notSpecified";
        }
        if(sortType == null) {
            sortType = "asc";
        }
        try {
            request.setAttribute("products", productDao.findProducts(
                    query,
                    SortField.valueOf(sortField),
                    SortType.valueOf(sortType)));
        } catch (IllegalArgumentException e) {
            request.setAttribute("products", productDao.findProducts(
                    query,
                    SortField.notSpecified,
                    SortType.asc));
        }
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
