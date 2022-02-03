package com.es.phoneshop.web;

import com.es.phoneshop.model.product.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
    super.init(config);
    productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String sortParam;
        if(SortingParams.isExist(request.getParameter("sortParam"))){
            sortParam = request.getParameter("sortParam");
        }else{
            sortParam = null;
        }
        request.setAttribute("products", productDao.findProducts(query,
                Optional.ofNullable(sortParam).map(SortingParams:: valueOf)
                        .orElse(null)));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
