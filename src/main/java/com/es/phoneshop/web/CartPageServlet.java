package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.service.CartService;
import com.es.phoneshop.model.cart.service.HttpServletCartService;
import com.es.phoneshop.model.product.service.ProductService;
import com.es.phoneshop.model.product.service.ProductServiceImpl;
import com.es.phoneshop.model.recentlyViewed.HttpServletRecentlyViewedService;
import com.es.phoneshop.model.recentlyViewed.RecentlyViewedService;
import com.es.phoneshop.web.exceptions.OutOfStockException;
import com.es.phoneshop.web.paramEnums.PostParamKeys;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));
        request.setAttribute("recentlyViewed", recentlyViewedService.getList(request));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }


    private int getParsedQuantity(String rawQuantity, Locale locale) throws ParseException{
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        return numberFormat.parse(rawQuantity).intValue();
    }


    private long parseId(String pathInfo) {
        try {
            return Integer.parseInt(pathInfo.split("/")[1]);
        } catch (NumberFormatException e) {
            throw new NoSuchElementException(pathInfo.split("/")[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NoSuchElementException(" ");
        }
    }
}
