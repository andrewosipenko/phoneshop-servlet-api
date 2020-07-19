package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ProductService;
import com.es.phoneshop.model.product.ProductServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productService = new ProductServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String req = Optional.ofNullable(request.getPathInfo()).orElse(" ");
        request.setAttribute("product", productService.getProduct(request.getPathInfo()));
        if(req.contains("priceHistory")) {
            request.getRequestDispatcher("/WEB-INF/pages/priceHistoryPage.jsp").forward(request, response);
        } else{
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        }

    }
}
