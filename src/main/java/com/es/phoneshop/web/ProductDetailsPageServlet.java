package com.es.phoneshop.web;

import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.ProductDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getPathInfo();
        if (id.length() == 1) {
            response.sendRedirect(request.getContextPath() + request.getServletPath());
        } else {
            id = id.substring(1);   
            if (!id.matches("\\d*")) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } else {
                try {
                    request.setAttribute("product", productDao.getProduct(Long.parseLong(id)));
                    request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
                } catch (IllegalArgumentException e) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        }
    }

}
