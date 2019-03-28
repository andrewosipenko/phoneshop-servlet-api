package com.es.phoneshop.web;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.dao.ArrayListProductDao;
import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductDao productDao = ArrayListProductDao.getInstance();

    protected static final String ID = "id";
    protected static final String PRODUCT = "product";

    protected enum Error {
        PARSE_ERROR("nfe"),
        OUT_OF_STOCK("oos");

        private final String errorCode;

        Error(String errorCode) {
            this.errorCode = errorCode;
        }

        static Optional<Error> indentify(String errorCode) {
            return Arrays.stream(Error.values())
                    .filter(error -> error.errorCode.equals(errorCode))
                    .findFirst();
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkOnErrors(req);
        Long productId = getProductId(req);
        req.setAttribute(ID, productId);
        req.setAttribute(PRODUCT, productDao.getProduct(productId));
        req.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(req, resp);
    }

    private void checkOnErrors(HttpServletRequest req) {
        if (req.getParameter("productAdded") != null) {
            req.setAttribute("productAdded", "Added to cart successfully");
        } else if (req.getParameter("err") != null) {
            String errorType = req.getParameter("err");
            Optional<Error> error;
            if((error = Error.indentify(errorType)).isPresent()) {
                switch (error.get()) {
                    case PARSE_ERROR:
                        req.setAttribute("error", "Not a number!");
                        break;
                    case OUT_OF_STOCK:
                        req.setAttribute("error", "Not enough stock!");
                        break;
                }
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Error errorType = null;
        Integer quantity = null;
        try {
            quantity = Integer.valueOf(req.getParameter("quantity"));
        } catch (NumberFormatException e) {
            errorType = Error.PARSE_ERROR;
        }
        if (quantity != null) {
            HttpSession session = req.getSession();
            if (session.getAttribute("httpCart") == null) {
                Cart cart = new Cart();
                session.setAttribute("httpCart", cart);
            }

            Long productId = getProductId(req);
            Cart customerCart = (Cart) session.getAttribute("httpCart");
            HttpSessionCartService httpSessionCartService = HttpSessionCartService.getInstance();

            try {
                httpSessionCartService.add(customerCart, productId, quantity);
                req.getServletContext().setAttribute("cart", customerCart);
                resp.sendRedirect(req.getRequestURI() + "?productAdded=ok");
                return;
            } catch (ProductNotFoundException e) {
                e.printStackTrace();
            } catch (OutOfStockException e) {
                errorType = Error.OUT_OF_STOCK;
            }

        }
        resp.sendRedirect(req.getRequestURI() + (errorType == null ? "" : ("?err=" + errorType.errorCode)));
    }

    private Long getProductId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        return Long.valueOf(pathInfo.substring(1));
    }
}
