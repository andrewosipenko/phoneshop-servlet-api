package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        int index = requestURI.indexOf(req.getServletPath());
        String productCode = requestURI.substring(index + req.getServletPath().length() + 1);

        try {
            Long productId = productDao.getProductByCode(productCode).getId();
            req.setAttribute("product", productDao.getProduct(productId));
            req.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(req, resp);
        } catch (ProductNotFoundException e) {
            req.setAttribute("code", productCode);
            req.getRequestDispatcher("/WEB-INF/pages/productNotFounded.jsp").forward(req, resp);
        }

    }
}
