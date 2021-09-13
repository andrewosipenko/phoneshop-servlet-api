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

public class ProductListPageServlet extends HttpServlet {
	
	ProductDao productDao;
	
	@Override
	public void init(ServletConfig config) throws ServletException{
		
		super.init(config);
		productDao = ArrayListProductDao.getInstance();
		
	}
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//For situations, when there's no such parameteres
        String field = request.getParameter("field");
        String order = request.getParameter("order");
        Filter filter;
        
        if(field == null && order == null && (request.getParameter("query") == null || "".equals(request.getParameter("query")) )) {
        	filter = new Filter(SortField.STOCK, SortOrder.DESC, null);
        } else {
        	filter = new Filter(
            		(field != null) ? SortField.valueOf(field.toUpperCase()) : null, 
            		(order != null) ? SortOrder.valueOf(order.toUpperCase()) : null, 
            		(String)request.getParameter("query"));
        }
        
    	request.setAttribute("products", productDao.findProducts(filter));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

}
