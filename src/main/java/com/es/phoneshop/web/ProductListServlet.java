package com.es.phoneshop.web;

import com.es.phoneshop.model.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDAO = ArrayListProductDAO.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", productDAO.findProducts());
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
