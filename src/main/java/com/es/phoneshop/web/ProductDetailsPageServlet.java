package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    ProductDao productDao;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdLine = request.getPathInfo();
        long productId;
        try{
            productId = Long.parseLong(productIdLine.substring(1));
        } catch (NumberFormatException exception){
            productId = -1L;
        }
        request.setAttribute("product", productDao.getProduct(productId));
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }
}
