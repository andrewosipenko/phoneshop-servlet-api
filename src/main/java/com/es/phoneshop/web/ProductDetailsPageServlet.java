package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.NotEnoughException;
import com.es.phoneshop.model.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ResourceBundle;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDAO productDAO;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDAO = ArrayListProductDAO.getInstance();
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idString = request.getPathInfo();

        try {
            request.setAttribute("product", productDAO.getProduct(Long.valueOf(idString.substring(1))));
            request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer quantity;
        //ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", request.getLocale());
        Long productId = Long.valueOf(getLastPathParameter(request));
        Product product = productDAO.getProduct(productId);
        try {
            quantity = DecimalFormat.getInstance(request.getLocale()).parse(request.getParameter("quantity")).intValue();
            if (quantity < 0) {
                throw new NotEnoughException(NotEnoughException.NOT_ENOUGH_MESSAGE);
            }
        } catch (ParseException e) {
            //exceptionError(product, request, response, resourceBundle.getString("error.number.format"));
            exceptionError(product, request, response, "Not a number");
            return;
        } catch (NotEnoughException e) {
            exceptionError(product, request, response, "Number must being > 0");
            return;
        }
        Cart cart = cartService.getCart(request);
        cartService.add(cart, product, quantity);
        request.setAttribute("addQuantity", quantity);
        //response.sendRedirect(request.getRequestURI() + "?addQuantity=" + quantity );
        showProductPage(product, request, response);
    }
    private String getLastPathParameter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int index = uri.lastIndexOf("/");
        return uri.substring(index+1);
    }

    private void showProductPage(Product product, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("product", product);
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }

        private void exceptionError(Product product, HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("error", Boolean.TRUE);
        request.setAttribute("errorText", message);
        request.setAttribute("product", product);
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }
}
