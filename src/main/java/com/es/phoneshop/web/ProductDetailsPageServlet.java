package com.es.phoneshop.web;

import com.es.phoneshop.model.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init(){
        productDao= ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String uri = request.getRequestURI();
        String productID = uri.substring(uri.lastIndexOf("/")+1);

        request.setAttribute("product", productDao.getProduct(Long.valueOf(productID)));
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);

    }
}
