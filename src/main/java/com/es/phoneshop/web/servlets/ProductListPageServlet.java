package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.service.CartService;
import com.es.phoneshop.model.cart.service.HttpServletCartService;
import com.es.phoneshop.model.product.service.ProductService;
import com.es.phoneshop.model.product.service.ProductServiceImpl;
import com.es.phoneshop.model.recentlyViewed.HttpServletRecentlyViewedService;
import com.es.phoneshop.model.recentlyViewed.RecentlyViewedService;
import com.es.phoneshop.web.constants.ControllerConstants;
import com.es.phoneshop.web.constants.GetParamKeys;
import com.es.phoneshop.web.constants.PostParamKeys;
import com.es.phoneshop.web.exceptions.OutOfStockException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ProductListPageServlet extends HttpServlet {
    //TODO Controller-layer
    private ProductService productService;
    private CartService<HttpServletRequest> cartService;
    private RecentlyViewedService<HttpServletRequest> panelService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productService = ProductServiceImpl.INSTANCE;
        cartService = HttpServletCartService.INSTANCE;
        panelService = HttpServletRecentlyViewedService.INSTANCE;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processDoGetRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInto = Optional.ofNullable(request.getPathInfo())
                .orElse(" ");
        String quantityParam = Optional.ofNullable(request.getParameter(String.valueOf(PostParamKeys.quantity)))
                .orElse(" ");
        String productIdParam = Optional.ofNullable(request.getParameter(String.valueOf(PostParamKeys.productId)))
                .orElse(" ");


        int quantity;

        try {
            NumberFormat numberFormat = NumberFormat.getInstance(request.getLocale());
            quantity = numberFormat.parse(quantityParam).intValue();
        } catch (ParseException e) {
            response.sendRedirect(request.getContextPath()
                    + "/products"
                    + "?productId=" + productIdParam
                    + "&quantity=" + quantityParam
                    + "&error=" + ControllerConstants.NOT_A_NUMBER_ERROR_MESSAGE);
            return;
        }

        try {
            cartService.add(cartService.getCart(request), parseId(pathInto), quantity);
        } catch (OutOfStockException e) {
            response.sendRedirect(request.getContextPath()
                    + "/products"
                    + "?productId=" + productIdParam
                    + "&quantity=" + quantityParam
                    + "&error=" + ControllerConstants.OUT_OF_STOCK_ERROR_MESSAGE);
            return;
        }

        response.sendRedirect(request.getContextPath()
                + "/products?productId=" + productIdParam
                + "&quantity=" + quantityParam
                + "&message=" + ControllerConstants.ADDING_TO_CART_SUCCESS_MESSAGE);
    }

    private void processDoGetRequest(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        String sortParam = Optional.ofNullable(request.getParameter(String.valueOf(GetParamKeys.sort))).orElse(" ");
        String orderParam = Optional.ofNullable(request.getParameter(String.valueOf(GetParamKeys.order))).orElse(" ");
        String searchParam = Optional.ofNullable(request.getParameter(String.valueOf(GetParamKeys.query))).orElse(" ");


        request.setAttribute("products", productService.findProducts(sortParam, orderParam, searchParam));
        request.setAttribute("recentlyViewed", panelService.getList(request));
        request.getRequestDispatcher(ControllerConstants.PRODUCT_LIST_JSP_PATH).forward(request, response);
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
