package com.es.phoneshop.web;

import com.es.phoneshop.model.product.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getPathInfo();
        try {
            Optional<Product> product = productDao.getProduct(Long.valueOf(url.substring(1)));
            request.setAttribute("product", product.get());
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        } catch (IllegalArgumentException | NoSuchElementException | NullPointerException e) {
            response.setStatus(404);
            request.setAttribute("incorrectId", url.substring(1));
            request.getRequestDispatcher("/WEB-INF/pages/PageNotFound.jsp").forward(request, response);
        }
    }
}
