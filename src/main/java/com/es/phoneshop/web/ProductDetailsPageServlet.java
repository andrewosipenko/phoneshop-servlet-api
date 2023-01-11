package com.es.phoneshop.web;

import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.dao.impl.ArrayListProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo().substring(1).contains("/")) {
            request.setAttribute("product", productDao.getProduct(
                    Long.parseLong(request.getPathInfo().substring(1, request.getPathInfo().lastIndexOf('/')))));
            request.getRequestDispatcher("/WEB-INF/pages/productPriceHistory.jsp").forward(request, response);
        } else {
            request.setAttribute("product", productDao.getProduct(
                    Long.parseLong(request.getPathInfo().substring(1))));
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        }
    }
}
