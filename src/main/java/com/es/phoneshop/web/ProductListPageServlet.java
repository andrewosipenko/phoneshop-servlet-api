package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.enums.sort.SortField;
import com.es.phoneshop.model.product.enums.sort.SortOrder;
import com.es.phoneshop.model.product.recentlyview.DefaultRecentlyViewService;
import com.es.phoneshop.model.product.recentlyview.RecentlyViewSection;
import com.es.phoneshop.model.product.recentlyview.RecentlyViewService;

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
    public static final String RECENTLY_VIEW_SECTION = "recentlyViewSection";

    private ArrayListProductDao arrayListProductDao;
    private RecentlyViewService recentlyViewService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        arrayListProductDao = ArrayListProductDao.getInstance();
        recentlyViewService = DefaultRecentlyViewService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchText = request.getParameter(SEARCH_TEXT);
        List<String> searchTextList = searchText != null ? parseSearchText(request) : null;
        String sortOrder = request.getParameter(SORT_ORDER);
        String sortField = request.getParameter(SORT_FIELD);
        RecentlyViewSection recentlyViewSection = recentlyViewService.getRecentlyViewSection(request);
        request.setAttribute(RECENTLY_VIEW_SECTION, recentlyViewSection);
        request.setAttribute("products", arrayListProductDao.findProducts(searchTextList,
                sortField != null ? SortField.valueOf(sortField) : null,
                sortOrder != null ? SortOrder.valueOf(sortOrder) : null));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

    private List<String> parseSearchText(HttpServletRequest request) {
        return Arrays.asList(request.getParameter(SEARCH_TEXT).split("\\s"));
    }
}
