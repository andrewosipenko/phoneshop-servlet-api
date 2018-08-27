package com.es.phoneshop.web;

import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.ProductDao;

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

        String id = getPageId(request);
        if(!id.matches("\\d*")){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            request.getRequestDispatcher("/WEB-INF/pages/error500.jsp").forward(request, response);
        }
        else {
            try {
                request.setAttribute("product", productDao.getProduct(Long.parseLong(id)));
                request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
            } catch (IllegalArgumentException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                request.getRequestDispatcher("/WEB-INF/pages/error404.jsp").forward(request, response);
            }
        }
    }

    private String getPageId(HttpServletRequest request){
        String uri = request.getRequestURI();
        int index = uri.lastIndexOf("/");
        return uri.substring(index + 1);
    }
}
