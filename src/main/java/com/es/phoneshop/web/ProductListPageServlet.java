package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    request.setAttribute("products", productDao.findProducts(query,
            getSortField(request), getSortOrder(request)));
    request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
  }

  private SortField getSortField(HttpServletRequest request) {
    String sortField = request.getParameter("sort");
    return sortField != null ? SortField.valueOf(sortField) : null;
  }

  private SortOrder getSortOrder(HttpServletRequest request) {
    String sortOrder = request.getParameter("order");
    return sortOrder != null ? SortOrder.valueOf(sortOrder) : null;
  }
}
