package com.es.phoneshop.web;

import com.es.phoneshop.model.*;
import com.es.phoneshop.cart.Cart;
import com.es.phoneshop.cart.CartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.NoSuchElementException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private HttpSession session;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getInstance();
        cartService = CartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productID = Long.parseLong(request.getPathInfo().substring(1));
        session = request.getSession();
        /*Enumeration<String> attributeNames = session.getAttributeNames();
        String attributeName = null;
        while (attributeNames.hasMoreElements()) {
            attributeName = attributeNames.nextElement();
            System.out.println(attributeName);
            request.setAttribute(attributeName, session.getAttribute(attributeName));
        }*/
        getAndRemoveSessionAttribute(session, request, "error");
        getAndRemoveSessionAttribute(session, request, "success");
        try {
            request.setAttribute("product", productDao.getProduct(productID));
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        } catch (NoSuchElementException ex) {
            request.getRequestDispatcher("/WEB-INF/pages/productNotFoundError").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = Long.valueOf(getLastPathParameter(request));
        Product product = productDao.getProduct(productId);
        Integer quantity;
        try {
            Locale locale = request.getLocale();
            quantity = DecimalFormat.getInstance(locale).parse(request.getParameter("quantity")).intValue();
        } catch (ParseException ex) {
            session.setAttribute("error", "Not a number");
            setAttributeProduct(product, request, response);
            return;
        }
        if (quantity<=0) {
            session.setAttribute("error", "Quantity must be >0");
            setAttributeProduct(product, request, response);
            return;
        }
        Cart cart = cartService.getCart(request);
        cartService.add(cart, product, quantity);
        session.setAttribute("success", "Products were successfully added.");
        setAttributeProduct(product, request, response);
    }

    private String getLastPathParameter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int index = uri.lastIndexOf("/");
        return uri.substring(index+1);
    }

    private void setAttributeProduct(Product product, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setAttribute("product", product);
        //request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        response.sendRedirect(request.getRequestURI());
    }

    private void getAndRemoveSessionAttribute(HttpSession session, HttpServletRequest request, String attributeName) {
        if (session.getAttribute(attributeName) != null) {
            request.setAttribute(attributeName, session.getAttribute(attributeName));
            session.removeAttribute(attributeName);
        }
    }
}
