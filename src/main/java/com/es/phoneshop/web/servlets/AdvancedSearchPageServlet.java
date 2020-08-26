package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.service.CartService;
import com.es.phoneshop.model.cart.service.HttpServletCartService;
import com.es.phoneshop.model.product.service.ProductService;
import com.es.phoneshop.model.product.service.ProductServiceImpl;
import com.es.phoneshop.model.recentlyViewed.HttpServletRecentlyViewedService;
import com.es.phoneshop.model.recentlyViewed.RecentlyViewedService;
import com.es.phoneshop.web.constants.ControllerConstants;
import com.es.phoneshop.web.constants.GetProductParamKeys;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AdvancedSearchPageServlet extends HttpServlet {
    private ProductService productService;
    private RecentlyViewedService<HttpServletRequest> panelService;

    private static final String PRODUCT_CODE_PARAM_VALUE = "productCode";
    private static final String MIN_PRICE_PARAM_VALUE = "minPrice";
    private static final String MAX_PRICE_PARAM_VALUE = "maxPrice";
    private static final String MIN_STOCK_PARAM_VALUE = "minStock";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productService = ProductServiceImpl.INSTANCE;
        panelService = HttpServletRecentlyViewedService.INSTANCE;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processDoGetRequest(request);
        request.getRequestDispatcher(ControllerConstants.ADVANCED_SEARCH_PAGE_JSP_PATH).forward(request, response);
    }

    private void processDoGetRequest(HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        String productCodeParam = Optional.ofNullable(request.getParameter(PRODUCT_CODE_PARAM_VALUE)).orElse(" ");
        String rawMinPriceParam = Optional.ofNullable(request.getParameter(MIN_PRICE_PARAM_VALUE)).orElse(" ");
        String rawMaxPriceParam = Optional.ofNullable(request.getParameter(MAX_PRICE_PARAM_VALUE)).orElse(" ");
        String rawMinStockParam = Optional.ofNullable(request.getParameter(MIN_STOCK_PARAM_VALUE)).orElse(" ");

        BigDecimal minPriceParam = parseToBigDecimalParam(rawMinPriceParam, MIN_PRICE_PARAM_VALUE, errors);
        BigDecimal maxPriceParam = parseToBigDecimalParam(rawMaxPriceParam, MAX_PRICE_PARAM_VALUE, errors);
        Integer minStockParam = parseToIntegerParam(rawMinStockParam, MIN_STOCK_PARAM_VALUE, errors);

        request.setAttribute("products", productService.advancedSearch(productCodeParam, minPriceParam, maxPriceParam, minStockParam));
        request.setAttribute("errors", errors);
        request.setAttribute("recentlyViewed", panelService.getList(request));
    }


    private BigDecimal parseToBigDecimalParam(String rawValue, String paramName, Map<String, String> errors) {
        try {
            return new BigDecimal(Integer.parseInt(rawValue));
        } catch (NumberFormatException e) {
            if(!rawValue.equals(" ")) {
                errors.put(paramName, "Not a number");
            }
            return null;
        }
    }

    private Integer parseToIntegerParam(String rawValue, String paramName, Map<String, String> errors) {
        try {
            return Integer.parseInt(rawValue);
        } catch (NumberFormatException e) {
            if(!rawValue.equals(" ")) {
                errors.put(paramName, "Not a number");
            }
            return null;
        }
    }
}
