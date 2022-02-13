package com.es.phoneshop.web;

import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.cart.DefaultCartService;
import com.es.phoneshop.exceptions.IncorrectInputException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class AddToCartFromPLPServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private String queryTest;
    private String sortParamTest;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
        queryTest = "";
        sortParamTest = "";
    }

    private boolean isProductIdExist(String productId) {
        if (Pattern.matches("^[0-9]+$", productId)) {
            return productDao.getProduct(Long.valueOf(productId)).isPresent();
        } else {
            return false;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getPathInfo().substring(1);
        queryTest = request.getParameter("query2");
        sortParamTest = request.getParameter("sortParam2");
        if (queryTest == null) {
            queryTest = "";
        }
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        int index = Arrays.asList(productIds).indexOf(productId);
        if (isProductIdExist(productId) || index != -1) {
            try {
                cartService.add(request, productId, quantities[index]);
                response.sendRedirect(request.getContextPath() + "/products?message=Cart updated successfully" +
                        "&query=" + queryTest + "&sortParam=" + sortParamTest);
            } catch (IncorrectInputException e) {
                String errorMessage = e.getErrorMessage();
                if (errorMessage.equals("Out of stock")) {
                    errorMessage = errorMessage + ", available " +
                            (productDao.getProduct(Long.valueOf(productId)).get().getStock()
                            - cartService.getCart(request).getCurrentQuantityById(Long.valueOf(productId)));
                }
                response.sendRedirect(request.getContextPath() + "/products?" +
                        "query=" + queryTest + "&sortParam=" + sortParamTest
                        + "&errorMessage=" + errorMessage + "&prId=" + productId);
            }
        } else {
            response.setStatus(404);
            request.setAttribute("id", productId);
            request.getRequestDispatcher("/WEB-INF/pages/errorProductNotFound.jsp").forward(request, response);
        }

    }
}

