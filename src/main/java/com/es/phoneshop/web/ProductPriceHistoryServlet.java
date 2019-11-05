package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ProductListService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductPriceHistoryServlet extends HttpServlet {
    private ProductListService productListService;

    @Override
    public void init() {
        productListService = new ProductListService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = Long.valueOf(request.getPathInfo().substring(1));

        request.setAttribute("product", productListService.getProduct(productId));
        request.getRequestDispatcher("/WEB-INF/pages/productsPrices.jsp").forward(request, response);
    }
}
