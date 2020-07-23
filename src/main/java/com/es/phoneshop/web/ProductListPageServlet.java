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

public class ProductListPageServlet extends HttpServlet {
    //TODO Controller-layer
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productService = new ProductServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        //could be used ternary expression to avoid null but i'm not sure if it's good decision
        String sortParam = Optional.ofNullable(request.getParameter(String.valueOf(QUERY_PARAM_KEYS.sort))).orElse(" ");
        String orderParam = Optional.ofNullable(request.getParameter(String.valueOf(QUERY_PARAM_KEYS.order))).orElse(" ");
        String searchParam = Optional.ofNullable(request.getParameter(String.valueOf(QUERY_PARAM_KEYS.query))).orElse(" ");


        request.setAttribute("products", productService.findProducts(sortParam, orderParam, searchParam));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
