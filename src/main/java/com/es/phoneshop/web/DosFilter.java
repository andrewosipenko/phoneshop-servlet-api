package com.es.phoneshop.web;

import com.es.phoneshop.services.DosService;
import com.es.phoneshop.services.impl.DosServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {
    DosService dosService;
    private final int TOO_MANY_REQUEST = 429;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        dosService = new DosServiceImpl();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String ip = request.getLocalAddr();
        if (dosService.isAllowed(ip)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendError(TOO_MANY_REQUEST, "Too many request");
        }
    }

    @Override
    public void destroy() {
    }
}
