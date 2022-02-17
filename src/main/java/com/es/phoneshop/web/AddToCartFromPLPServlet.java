package com.es.phoneshop.web;

import com.es.phoneshop.cart.CartService;
import com.es.phoneshop.cart.DefaultCartService;
import com.es.phoneshop.exceptions.IncorrectInputException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.SortingParams;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class AddToCartFromPLPServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = DefaultCartService.getInstance();
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
        String query = request.getParameter("query2");
        String sortParam = request.getParameter("sortParam2");
        String quantity = request.getParameter("quantity");
        if (request.getPathInfo() != null &&
                isProductIdExist(request.getPathInfo().substring(1))) {
            // product for sure exists
            try {
                String productId = request.getPathInfo().substring(1);
                if (query == null) {
                    query = "";
                }
                if (sortParam == null) {
                    sortParam = String.valueOf(SortingParams.defaultSort);
                }
                cartService.add(request, productId, quantity);
                response.sendRedirect(request.getContextPath() + "/products?message=Cart updated successfully" +
                        "&query=" + query + "&sortParam=" + sortParam);
            } catch (IncorrectInputException e) {
                String errorMessage = e.getErrorMessage();
                if (errorMessage.equals("Out of stock")) {
                    errorMessage = errorMessage + ", available " +
                            (productDao.getProduct(Long.valueOf(request.getPathInfo().substring(1))).get().getStock()
                                    - cartService.getCart(request)
                                    .getCurrentQuantityById(Long.valueOf(request.getPathInfo().substring(1))));
                }
                response.sendRedirect(request.getContextPath() + "/products?" +
                        "query=" + query + "&sortParam=" + sortParam
                        + "&errorMessage=" + errorMessage + "&prId=" +
                        request.getPathInfo().substring(1));
            }
        } else {
            // product not found or not exists
            response.setStatus(404);
            if (request.getPathInfo() != null) {
                request.setAttribute("id", request.getPathInfo().substring(1));
            } else {
                request.setAttribute("id", "");
            }
            request.getRequestDispatcher("/WEB-INF/pages/errorProductNotFound.jsp").forward(request, response);
        }

    }
}

