package com.es.phoneshop.web;

import com.es.phoneshop.model.product.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getPathInfo().substring(1);
        Product product;
        if(Pattern.matches("^[0-9]+$",productId)) {
            product = productDao.getProduct(Long.valueOf(productId)).orElse(null);
        }else{
            product = null;
        }
        if (product == null) {
            response.setStatus(404);
            request.setAttribute("id",productId);
            request.getRequestDispatcher("/WEB-INF/pages/errorProductNotFound.jsp").forward(request,response);
        } else {
            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        }
    }

}

