package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;

   @Override
    public void init(){
       productDao=ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String sortFieldString=request.getParameter("sort");
       String sortOrderString=request.getParameter("order");

       SortField sortField = sortFieldString== null ? null : SortField.valueOf(sortFieldString.toUpperCase());
       SortOrder sortOrder = sortOrderString == null ? null : SortOrder.valueOf(sortOrderString.toUpperCase());

       request.setAttribute("products",
               productDao.findProducts(request.getParameter("query"), sortField, sortOrder));

       request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
   }
}


