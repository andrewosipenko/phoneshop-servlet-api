package com.es.phoneshop.web;

import model.ArrayListProductDao;
import model.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    ProductDao productDao;
    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //super.doGet(req, resp);
        String[] splitedContextPath = request.getRequestURI().split("/");
        Long productID = Long.parseLong(splitedContextPath[splitedContextPath.length-1]);
        request.setAttribute("product", productDao.getProduct(productID));
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }
}
