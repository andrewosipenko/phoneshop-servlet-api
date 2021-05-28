package com.es.phoneshop.web;

import com.es.phoneshop.domain.product.persistence.ProductDao;
import com.es.phoneshop.infra.config.Configuration;
import com.es.phoneshop.infra.config.ConfigurationImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

public class ProductPricesHistoryServlet extends HttpServlet {

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
            Long id = Long.valueOf(productIdStr);
            request.setAttribute("product", productDao.getById(id).get());
            request.setAttribute("prices", productDao.getPricesHistoryByProductId(id));

            request.getRequestDispatcher("/WEB-INF/pages/productPrices.jsp").forward(request, response);
        } catch (NumberFormatException | NoSuchElementException e){
            request.setAttribute("productIdStr", productIdStr);
            response.setStatus(404);
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp").forward(request, response);
        }
    }
}
