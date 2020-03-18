package com.es.phoneshop.web;

import com.es.phoneshop.service.DosService;
import com.es.phoneshop.service.impl.DefaultDosService;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {
    private static final int TOO_MANY_REQUESTS_ERROR_CODE = 429;

    private DosService dosService;

    @Override
    public void init(FilterConfig filterConfig) {
        dosService = DefaultDosService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (dosService.isAllowed(servletRequest.getRemoteAddr())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendError(TOO_MANY_REQUESTS_ERROR_CODE);
        }
    }

    @Override
    public void destroy() {
    }
}
