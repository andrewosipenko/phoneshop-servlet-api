package com.es.phoneshop.web.firstRequest;

import javax.servlet.http.HttpServletRequest;

public interface FirstRequestService {
    boolean isFirstRequest(HttpServletRequest request);
}
