package com.es.phoneshop.web.servlets;

import com.es.phoneshop.model.cart.service.CartService;
import com.es.phoneshop.model.cart.service.HttpServletCartService;
import com.es.phoneshop.model.product.service.ProductService;
import com.es.phoneshop.model.product.service.ProductServiceImpl;
import com.es.phoneshop.model.recentlyViewed.HttpServletRecentlyViewedService;
import com.es.phoneshop.model.recentlyViewed.RecentlyViewedService;
import com.es.phoneshop.web.constants.ControllerConstants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductService productService;
    private CartService<HttpServletRequest> cartService;
    private RecentlyViewedService<HttpServletRequest> recentlyViewedService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productService = ProductServiceImpl.INSTANCE;
        cartService = HttpServletCartService.INSTANCE;
        recentlyViewedService = HttpServletRecentlyViewedService.INSTANCE;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInto = Optional.ofNullable(request.getPathInfo())
                .orElse(" ");

        var product = productService.getProduct(pathInto);
        request.setAttribute("product", product);
        recentlyViewedService.add(recentlyViewedService.getList(request), product);
        request.setAttribute("cart", cartService.getCart(request));
        request.setAttribute("recentlyViewed", recentlyViewedService.getList(request));

        if (pathInto.contains("priceHistory")) {
            request.getRequestDispatcher(ControllerConstants.PRICE_HISTORY_JSP_PATH).forward(request, response);
        } else {
            request.getRequestDispatcher(ControllerConstants.PRODUCT_DETAILS_JSP_PATH).forward(request, response);
        }

    }
}
