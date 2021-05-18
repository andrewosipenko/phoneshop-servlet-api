package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.service.CartService;
import com.es.phoneshop.model.cart.service.HttpServletCartService;
import com.es.phoneshop.model.recentlyViewed.HttpServletRecentlyViewedService;
import com.es.phoneshop.model.recentlyViewed.RecentlyViewedService;
import com.es.phoneshop.web.constants.ControllerConstants;
import com.es.phoneshop.web.exceptions.OutOfStockException;
import com.es.phoneshop.web.constants.PostProductParamKeys;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public class CartPageServlet extends HttpServlet {
    private CartService<HttpServletRequest> cartService;
    private RecentlyViewedService<HttpServletRequest> recentlyViewedService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = HttpServletCartService.INSTANCE;
        recentlyViewedService = HttpServletRecentlyViewedService.INSTANCE;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));
        request.setAttribute("recentlyViewed", recentlyViewedService.getList(request));
        request.getRequestDispatcher(ControllerConstants.CART_JSP_PATH).forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<Long, String> errors = new HashMap<>();

        String[] productIds = request.getParameterValues(String.valueOf(PostProductParamKeys.productId));
        String[] quantities = request.getParameterValues(String.valueOf(PostProductParamKeys.quantity));

        for (int i = 0; i < productIds.length; i++) {
            try {
                int quantity = getParsedQuantity(quantities[i], request.getLocale());
                cartService.update(cartService.getCart(request), Long.valueOf(productIds[i]), quantity);
            } catch (ParseException e) {
                errors.put(Long.valueOf(productIds[i]), ControllerConstants.NOT_A_NUMBER_ERROR_MESSAGE);
            } catch (OutOfStockException e) {
                errors.put(Long.valueOf(productIds[i]), ControllerConstants.OUT_OF_STOCK_ERROR_MESSAGE);
            }
        }

        if(errors.isEmpty()){
            response.sendRedirect(request.getContextPath() + "/cart?message=Cart updated successfully");
        } else {
            request.setAttribute("errors", errors);
            doGet(request, response);
        }
    }

    private int getParsedQuantity(String rawQuantity, Locale locale) throws ParseException{
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        return numberFormat.parse(rawQuantity).intValue();
    }
}
