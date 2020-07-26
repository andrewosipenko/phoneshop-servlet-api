package com.es.phoneshop.web;

import com.es.phoneshop.model.product.service.ProductService;
import com.es.phoneshop.model.product.service.ProductServiceImpl;
import com.es.phoneshop.model.recentlyViewed.HttpServletRecentlyViewedService;
import com.es.phoneshop.model.recentlyViewed.RecentlyViewedService;
import com.es.phoneshop.web.paramEnums.GetParamKeys;

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
    private RecentlyViewedService<HttpServletRequest> panelService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productService = ProductServiceImpl.INSTANCE;
        panelService = HttpServletRecentlyViewedService.INSTANCE;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        String sortParam = Optional.ofNullable(request.getParameter(String.valueOf(GetParamKeys.sort))).orElse(" ");
        String orderParam = Optional.ofNullable(request.getParameter(String.valueOf(GetParamKeys.order))).orElse(" ");
        String searchParam = Optional.ofNullable(request.getParameter(String.valueOf(GetParamKeys.query))).orElse(" ");


        request.setAttribute("products", productService.findProducts(sortParam, orderParam, searchParam));
        request.setAttribute("recentlyViewed", panelService.getList(request));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
