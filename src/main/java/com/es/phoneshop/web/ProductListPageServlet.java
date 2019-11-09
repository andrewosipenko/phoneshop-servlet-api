package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductListService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProductListPageServlet extends HttpServlet {
    private ProductListService productListService;

    @Override
    public void init() {
        productListService = new ProductListService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String sortField = request.getParameter("sort");
        String sortOrder = request.getParameter("order");
        List<Product> productList = productListService.findProducts(query, sortField, sortOrder);

        request.setAttribute("products", productList);
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
