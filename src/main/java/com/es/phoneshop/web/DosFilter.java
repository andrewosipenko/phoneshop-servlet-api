package com.es.phoneshop.web;

import com.es.phoneshop.model.product.security.DefaultDosService;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {

    DefaultDosService dosService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        dosService = DefaultDosService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (dosService.isAllowed(servletRequest.getRemoteAddr())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).setStatus(429);
        }
    }

    @Override
    public void destroy() {
    }
}