package com.es.phoneshop.web;


import com.es.phoneshop.logic.MostViewedProducts;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.CartService.*;
import com.es.phoneshop.logic.ViewedProducts;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import static com.es.phoneshop.projectConstants.Constants.*;

public class ProductListPageDetailsServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) {
        this.productDao = ArrayListProductDao.getInstance();
        this.cartService = CartService.getInstance();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String productCode = uri.substring(uri.lastIndexOf("/") + 1);
        Product product = productDao.getProduct(Long.parseLong(productCode));
        request.setAttribute("product", product);
        request.setAttribute(QUANTITY_ANSWER, request.getParameter(QUANTITY_ANSWER));
        request.setAttribute(QUANTITY, request.getParameter(QUANTITY));
        productDao.getProduct(Long.parseLong(productCode)).incrementAmmountOfViews();

        HttpSession session = request.getSession();
        MostViewedProducts mostPopularProducts = (MostViewedProducts) session.getAttribute("mostViewed");
        if(mostPopularProducts == null){
            mostPopularProducts = new MostViewedProducts();
            mostPopularProducts.update();
            session.setAttribute("mostViewed", mostPopularProducts);
        }
        else{
            mostPopularProducts.update();
            session.setAttribute("mostViewed", mostPopularProducts);
        }


        ViewedProducts viewedProducts = (ViewedProducts) session.getAttribute(VIEWED_PRODUCTS);
        if (viewedProducts == null) {
            viewedProducts = new ViewedProducts();
            viewedProducts.add(product);
            session.setAttribute(VIEWED_PRODUCTS, viewedProducts);
        } else {
            viewedProducts.add(product);
            session.setAttribute(VIEWED_PRODUCTS, viewedProducts);
        }

        request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, responce);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        Product product = productDao.getProduct(Long.parseLong(request.getParameter(PROUDUCT_ID)));

        if (cartService.addToCart(request, product, request.getParameter(QUANTITY))) {
            String path = request.getContextPath() + "/products/" + request.getParameter(PROUDUCT_ID) + "?quantityAnswer=" + "Product was added!" + "&quantity=" + request.getParameter(QUANTITY);
            responce.sendRedirect(path);
        } else {
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, responce);
        }
    }

}

