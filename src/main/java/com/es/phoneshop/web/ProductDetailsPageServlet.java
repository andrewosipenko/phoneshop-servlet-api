package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.NotEnoughElementsException;
import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.RecentlyViewedService;
import com.es.phoneshop.services.impl.CartServiceImpl;
import com.es.phoneshop.services.impl.RecentlyViewedServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private RecentlyViewedService recentlyViewedService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long productId = getProductIdFromUrl(req);
        try {
            req.setAttribute("product", productDao.getById(productId));
            recentlyViewedService.add(productId, req);
            req.setAttribute("viewedProducts", recentlyViewedService.getViewedProducts(req));
            req.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(req, resp);
        } catch (NoSuchElementException e) {
            resp.sendError(404, "Product with id= " + productId.toString() + " not found!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long quantity = Long.parseLong(req.getParameter("quantity"));
            Long productId = getProductIdFromUrl(req);
            cartService.add(cartService.getCart(req), productId, quantity);
            req.setAttribute("congratulation", "Added to cart successfully");
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Not a number");
        } catch (NotEnoughElementsException e) {
            req.setAttribute("error", "Not enough stock");
        } finally {
            doGet(req, resp);
        }
    }

    private Long getProductIdFromUrl(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        return Long.parseLong(pathInfo.split("/")[1]);
    }

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
        cartService = CartServiceImpl.getInstance();
        recentlyViewedService = RecentlyViewedServiceImpl.getInstance();
    }
}