package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ProductListPageServlet extends HttpServlet {

    private static final String SEARCH_TEXT = "searchText";
    private static final String SORT_ORDER = "sortOrder";
    private static final String SORT_FIELD = "sortField";

    private ArrayListProductDao arrayListProductDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        arrayListProductDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String searchText = request.getParameter(SEARCH_TEXT);
        List<String> searchTextList = searchText != null ?
                Arrays.asList(request.getParameter(SEARCH_TEXT).split("\\s")) : null;
        String sortOrder = request.getParameter(SORT_ORDER);
        String sortField = request.getParameter(SORT_FIELD);
        request.setAttribute("products", arrayListProductDao.findProducts(searchTextList,
                sortField != null ? SortField.valueOf(sortField) : null,
                sortOrder != null ? SortOrder.valueOf(sortOrder) : null));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
