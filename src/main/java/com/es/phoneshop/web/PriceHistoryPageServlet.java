package com.es.phoneshop.web;

import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ProductDao;
import com.es.phoneshop.services.RecentlyViewedService;
import com.es.phoneshop.services.impl.RecentlyViewedServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PriceHistoryPageServlet extends HttpServlet {

    ProductDao productDao;
    RecentlyViewedService recentlyViewedService;

    @Override
    public void init() throws ServletException {
        System.out.println("working priceHistoryPageServlet init method");
        productDao = ArrayListProductDao.getInstance();
        recentlyViewedService = RecentlyViewedServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        Long productId = Long.parseLong(pathInfo.split("/")[1]);
        Product product = productDao.getProduct(productId);
        req.setAttribute("product", product);
        req.setAttribute("viewedProducts", recentlyViewedService.getViewedProducts(req));
        req.getRequestDispatcher("/WEB-INF/pages/priceHistory.jsp").forward(req, resp);
    }
}
