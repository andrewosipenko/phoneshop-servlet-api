package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.model.filter.Filter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class ProductListPageServlet extends HttpServlet {
	
	ProductDao productDao;
	
	@Override
	public void init(ServletConfig config) throws ServletException{
		
		super.init(config);
		productDao = new ArrayListProductDao();
		
	}
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Filter filter = new Filter(SortField.DESCRIPTION, SortOrder.ASC, (String)request.getParameter("query"));
    	request.setAttribute("products", productDao.findProducts(filter));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

}
