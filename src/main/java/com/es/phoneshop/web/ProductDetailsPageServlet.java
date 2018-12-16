package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ViewedProductsList;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.ViewedProductsService;
import com.es.phoneshop.service.impl.CartServiceImpl;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.service.impl.ViewedProductsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private ViewedProductsService viewedProductsService;
    private Queue<Product> viewedProducts = new LinkedList<>();


    @Override
    public void init() throws ServletException {
        super.init();

        productDao = ArrayListProductDao.getInstance();
        cartService = CartServiceImpl.getInstance();
        viewedProductsService = ViewedProductsServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Product product = loadProduct(request);

            ViewedProductsList viewedProducts = (ViewedProductsList) request.getSession().getAttribute("viewedProducts");
            if (viewedProducts == null) {
                viewedProducts = new ViewedProductsList();
            }
            viewedProductsService.addToViewed(product, viewedProducts);
            request.getSession().setAttribute("viewedProducts", viewedProducts);

            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        } catch (ClassCastException | IllegalArgumentException e) {
            response.sendError(404, "Incorrect format of quantity");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = loadProduct(request);

        request.setAttribute("product", product);
        request.setAttribute("cart", cartService.getCart(request.getSession()).getCartItems());

        Integer quantity = null;
        try {
            String quantityString = request.getParameter("quantity");
            quantity = Integer.valueOf(quantityString);
        } catch (NumberFormatException e) {
            request.setAttribute("quantityError", "Not a number");
        }
        if (quantity != null) {
            cartService.addToCart(cartService.getCart(request.getSession()), product, quantity);
            request.setAttribute("message", "Product added successfully");
            response.sendRedirect(request.getRequestURL() + "?message=Product added successfully");
        } else {
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        }

    }

    private Product loadProduct(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String stringId = url.substring(url.lastIndexOf("/") + 1);
        Long id = Long.parseLong(stringId);
        return productDao.getProduct(id);
    }


}
