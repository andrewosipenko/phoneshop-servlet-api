package com.es.phoneshop.web;

import com.es.phoneshop.service.ProductService;
import com.es.phoneshop.service.ProductServiceImpl;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productService = ProductServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getPathInfo();
        productId = productId.substring(1);
        if (productId.contains("/")) {
            productId = productId.substring(0, productId.indexOf('/'));
        }
        request.setAttribute("product", productService.getProduct(Long.parseLong(productId)).get());
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

}
