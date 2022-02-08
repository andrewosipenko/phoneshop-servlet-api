package com.es.phoneshop.web;

import com.es.phoneshop.model.product.SortField;
import com.es.phoneshop.model.product.SortOrder;
import com.es.phoneshop.model.product.bean.Product;
import com.es.phoneshop.model.product.bean.RecentViewCart;
import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.service.RecentViewedService;
import com.es.phoneshop.model.product.service.RecentViewedServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductListPageServlet extends HttpServlet {
    private static final String RECENT_VIEWED = "recentViewed";
    private static final String QUERY = "query";
    private static final String PRODUCTS = "products";
    private static final String SORT = "sort";
    private static final String ORDER = "order";
    private ProductDao productDao;
    private RecentViewedService recentViewed;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        recentViewed = RecentViewedServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        String query = request.getParameter(QUERY);
        String sortField = request.getParameter(SORT);
        String sortOrder = request.getParameter(ORDER);
        SortField field = Optional.ofNullable(sortField)
                .map(s -> SortField.valueOf(s.toUpperCase())).orElse(null);
        SortOrder order = Optional.ofNullable(sortOrder)
                .map(s -> SortOrder.valueOf(sortOrder.toUpperCase()))
                .orElse(null);
        request.setAttribute(PRODUCTS, productDao.findProducts(query, field, order));
        List<Product> productList = recentViewed
                .getRecentViewed(httpSession)
                .getItems();
        request.setAttribute(RECENT_VIEWED, productList
                .stream()
                .filter(p-> {
                    if(productList.size()<4) return true;
                    return productList.indexOf(p) != 0;
                })
                .collect(Collectors.toList())
        );
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
