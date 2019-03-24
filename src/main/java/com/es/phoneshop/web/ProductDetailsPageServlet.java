package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao = ArrayListProductDao.getInstance();
    protected static final String ID = "id";
    protected static final String PRODUCT = "product";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        Long productId = Long.valueOf(pathInfo.substring(1));

        req.setAttribute(ID, productId);
        req.setAttribute(PRODUCT, productDao.getProduct(productId));
        req.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(req, resp);
    }
}
