package com.es.phoneshop.web;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.recentviews.DefaultRecentViewService;
import com.es.phoneshop.model.recentviews.RecentView;
import com.es.phoneshop.model.recentviews.RecentViewService;
import com.es.phoneshop.model.sortenum.SortField;
import com.es.phoneshop.model.sortenum.SortOrder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;
    private RecentViewService recentViewService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        recentViewService = DefaultRecentViewService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter(RequestParameter.QUERY);
        SortField sortField = SortField.valueOf(request.getParameter(RequestParameter.SORT));
        SortOrder sortOrder = SortOrder.valueOf(request.getParameter(RequestParameter.ORDER));
        RecentView recentView = recentViewService.getRecentView(request);

        request.setAttribute(RequestParameter.RECENT_VIEWS, recentView.getRecentlyViewed());
        request.setAttribute(RequestParameter.PRODUCTS, productDao.findProducts(searchQuery, sortField, sortOrder));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }


}
