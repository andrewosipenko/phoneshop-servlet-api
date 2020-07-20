package com.es.phoneshop.web;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.services.RecentlyViewedService;
import com.es.phoneshop.services.impl.RecentlyViewedServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {

    private ProductDao productDao;
    private RecentlyViewedService recentlyViewedService;

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
        recentlyViewedService = RecentlyViewedServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("searcher");
        String order = request.getParameter("order");
        String sort = request.getParameter("sort");
        request.setAttribute("products", productDao.findProducts(search, order, sort));
        request.setAttribute("viewedProducts", recentlyViewedService.getViewedProducts(request));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
