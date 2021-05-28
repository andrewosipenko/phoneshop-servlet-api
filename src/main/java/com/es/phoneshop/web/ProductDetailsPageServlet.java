package com.es.phoneshop.web;

import com.es.phoneshop.infra.config.Configuration;
import com.es.phoneshop.infra.config.ConfigurationImpl;
import com.es.phoneshop.domain.product.persistence.ProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

public class ProductDetailsPageServlet extends HttpServlet {

    private Configuration configuration;

    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        configuration = ConfigurationImpl.getInstance();
        productDao = configuration.getProductDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdStr = request.getPathInfo().substring(1);
        try {
            request.setAttribute("product", productDao.getById(Long.valueOf(productIdStr)).get());
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        } catch (NumberFormatException | NoSuchElementException e){
            request.setAttribute("productIdStr", productIdStr);
            response.setStatus(404);
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp").forward(request, response);
        }
    }
}
