package com.es.phoneshop.web;

import com.es.phoneshop.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.smartcardio.CardTerminal;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

public class ProductDetaiilsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartServise;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getInstance();
        cartServise = CartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long productId = Long.parseLong(request.getPathInfo().substring(1));
        try {
            request.setAttribute("product", productDao.getProduct(productId));
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer quantity;
        Long productId = Long.parseLong(request.getPathInfo().substring(1));
        Product product = productDao.getProduct(productId);
        Locale locale = request.getLocale();
        try {
            quantity = DecimalFormat.getInstance(locale).parse(request.getParameter("quantity")).intValue();
        } catch (NumberFormatException ex) {
            catchExeption(product, "error", request, response);
            return;
        } catch (ParseException ex) {
            catchExeption(product, "NaN", request, response);
            return;
        }
        Cart cart = cartServise.getCart(request);
        cartServise.add(cart, product, quantity);
        request.setAttribute("addQuantity", quantity);
        response.sendRedirect(request.getRequestURI() + "?addQuantity=" + quantity);
    }

    private void catchExeption(Product product, String type, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("error", Boolean.TRUE);
        request.setAttribute("errorText", type);
        request.setAttribute("product", product);
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }


}
