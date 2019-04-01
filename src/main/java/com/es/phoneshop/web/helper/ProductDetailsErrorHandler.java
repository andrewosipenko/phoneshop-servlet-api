package com.es.phoneshop.web.helper;

import javax.servlet.http.HttpServletRequest;

public class ProductDetailsErrorHandler implements ErrorHandler {
    protected static final String PRODUCT_ADDED_KEY = "productAdded";
    protected static final String ERROR_KEY = "err";
    protected static final String ERROR = "error";
    protected static final String SUCCESSFULLY_ADDED_MESSAGE = "Added to cart successfully";

    @Override
    public void handle(HttpServletRequest req) {
        String errorType;
        if (req.getParameter(PRODUCT_ADDED_KEY) != null) {
            req.setAttribute(PRODUCT_ADDED_KEY, SUCCESSFULLY_ADDED_MESSAGE);
        } else if ((errorType = req.getParameter(ERROR_KEY)) != null) {
            Error error = Error.identify(errorType);
            req.setAttribute(ERROR, error.getErrorMessage());
        }
    }
}
