package com.es.phoneshop.web.helper;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ProductDetailsErrorHandler implements ErrorHandler {

    @Override
    public void handle(HttpServletRequest req) {
        if (req.getParameter("productAdded") != null) {
            req.setAttribute("productAdded", "Added to cart successfully");
        } else if (req.getParameter("err") != null) {
            String errorType = req.getParameter("err");
            Optional<Error> error;
            if ((error = Error.indentify(errorType)).isPresent()) {
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
}
