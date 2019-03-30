package com.es.phoneshop.web.helper;

import javax.servlet.http.HttpServletRequest;

public interface ErrorHandler {
    void handle(HttpServletRequest request);
}
