package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.service.BrowsingHistoryService;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.BrowsingHistoryServiceImpl;
import com.es.phoneshop.service.impl.CartServiceImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.text.NumberFormat;
import java.text.ParseException;

public class AbstractServlet extends HttpServlet {
    protected CartService cartService;
    protected ProductDao productDao;
    protected BrowsingHistoryService browsingHistoryService;
    private static final String ERROR_MESSAGE = "Not a number";

    @Override
    public void init() {
        cartService = CartServiceImpl.getInstance();
        productDao = ArrayListProductDao.getInstance();
        browsingHistoryService = BrowsingHistoryServiceImpl.getInstance();
    }

    protected int validateQuantityInput(HttpServletRequest request, String quantityValue) throws ParseException {
        if (!quantityValue.matches("^([1-9]\\,?0*)?|([1-9]\\d+\\,?0*)?$")) {
            throw new NumberFormatException(ERROR_MESSAGE);
        }
        NumberFormat format = NumberFormat.getInstance(request.getLocale());
        return format.parse(quantityValue).intValue();
    }

    protected Long getProductIdFromUrl(HttpServletRequest request) {
        return Long.valueOf(request.getPathInfo().substring(1));
    }
}
