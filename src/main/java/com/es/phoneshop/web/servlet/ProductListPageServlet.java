package com.es.phoneshop.web.servlet;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.cartService.CartService;
import com.es.phoneshop.model.cart.cartService.impl.DefaultCartService;
import com.es.phoneshop.model.enums.SortField;
import com.es.phoneshop.model.enums.SortOrder;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);

        cartService.addToRecentlyViewed(cart, cart.getLastViewedProduct(), 3);
        request.setAttribute("recentlyViewed", cart.getRecentlyViewedProducts());

        String query = request.getParameter("query");
        SortField sortField = Optional.ofNullable(request.getParameter("sort"))
                .map(SortField::valueOf)
                .orElse(null);
        SortOrder sortOrder = Optional.ofNullable(request.getParameter("order"))
                .map(SortOrder::valueOf)
                .orElse(null);
        request.setAttribute("products", productDao.findProducts(query, sortField, sortOrder));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
