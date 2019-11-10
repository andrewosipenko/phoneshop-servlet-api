package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Deque;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private ViewedProductsService viewedProducts;
    private static final String QUANTITY_PARAMETER = "quantity";

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        viewedProducts = HttpSessionViewedProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = productDao.getProduct(extractId(request));
        Deque<Product> dequeViewedProducts = viewedProducts.getViewedProducts(request.getSession());
        request.setAttribute("viewedProducts", dequeViewedProducts);
        viewedProducts.addViewedProducts(viewedProducts.getViewedProducts(request.getSession()), product);
        request.setAttribute("product", productDao.getProduct(extractId(request)));
        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int quantity;
        try {
            quantity = Integer.parseInt(request.getParameter(QUANTITY_PARAMETER));
        } catch (NumberFormatException e) {
            sendError("Quantity should be a number", request, response);
            return;
        }
        response.sendRedirect(request.getRequestURI() + "?success=true");
    }

    private void sendError(String message, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        request.setAttribute(QUANTITY_PARAMETER, request.getParameter(QUANTITY_PARAMETER));
        doGet(request, response);
    }

    private Long extractId(HttpServletRequest request){
        return Long.parseLong(request.getPathInfo().replaceAll("/", ""));
    }

}
