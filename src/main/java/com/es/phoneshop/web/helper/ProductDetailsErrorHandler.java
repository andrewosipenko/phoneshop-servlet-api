package com.es.phoneshop.web.helper;

import javax.servlet.http.HttpServletRequest;

public class ProductDetailsErrorHandler implements ErrorHandler {
    protected static final String PRODUCT_ADDED_KEY = "productAdded";
    protected static final String ERROR_KEY = "err";
    protected static final String ERROR = "error";
    protected static final String SUCCESSFULLY_ADDED_MESSAGE = "Added to cart successfully";
    protected static final String PARSE_ERROR_MESSAGE = "Not a number!";
    protected static final String OUT_OF_STOCK_ERROR_MESSAGE = "Not enough stock!";
    protected static final String UNKNOWN_ERROR_MESSAGE = "Unknown error!";

    @Override
    public void handle(HttpServletRequest req) {
        String errorType;
        if (req.getParameter(PRODUCT_ADDED_KEY) != null) {
            req.setAttribute(PRODUCT_ADDED_KEY, SUCCESSFULLY_ADDED_MESSAGE);
        } else if ((errorType = req.getParameter(ERROR_KEY)) != null) {
            Error error = Error.identify(errorType);
            switch (error) {
                case PARSE_ERROR:
                    req.setAttribute(ERROR, PARSE_ERROR_MESSAGE);
                    break;
                case OUT_OF_STOCK:
                    req.setAttribute(ERROR, OUT_OF_STOCK_ERROR_MESSAGE);
                    break;
                case UNKNOWN:
                    req.setAttribute(ERROR, UNKNOWN_ERROR_MESSAGE);
                    break;
            }
        }
    }
}
