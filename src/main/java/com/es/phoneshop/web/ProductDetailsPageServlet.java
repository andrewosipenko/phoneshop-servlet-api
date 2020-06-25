package com.es.phoneshop.web;

import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        Long productId = Long.parseLong(pathInfo.split("/")[1]);
        try {
            req.setAttribute("product", productDao.getProduct(productId));
            req.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(req, resp);
        } catch (NoSuchElementException e) {
            resp.sendError(404, "Product with id= " + productId.toString() + " not found!");
        }
    }

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
    }
}
