package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

public class ProductDetailsPageServlet extends HttpServlet {
    ProductDao productDao;

    @Override
    public void init() throws ServletException {
        super.init();

        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int prefixWithSlashLength = (request.getContextPath() + request.getServletPath()).length() + 1;
        String id = request.getRequestURI().substring(prefixWithSlashLength);

        try {
            request.setAttribute("product", productDao.getProduct(Long.valueOf(id)));
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        } catch (RuntimeException e){
            request.getRequestDispatcher("/WEB-INF/pages/404.jsp").forward(request, response);
        }
    }

}
