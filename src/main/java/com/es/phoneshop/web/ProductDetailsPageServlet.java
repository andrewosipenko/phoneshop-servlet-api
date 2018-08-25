package com.es.phoneshop.web;

import model.ArrayListProductDAO;
import model.ProductDAO;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDAO = ArrayListProductDAO.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI();
        int index = request.getRequestURI().lastIndexOf("/");
        String idString = uri.substring(index + 1);
        request.setAttribute("product", productDAO.getProduct(Long.valueOf(idString)));
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }
}
