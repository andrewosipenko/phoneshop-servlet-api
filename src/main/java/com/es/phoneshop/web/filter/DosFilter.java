package com.es.phoneshop.web.filter;

import com.es.phoneshop.web.security.DosProtectionService;
import com.es.phoneshop.web.security.MapDosProtectionService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {
    private DosProtectionService dosProtectionService;

    @Override
    public void init(FilterConfig filterConfig) {
        dosProtectionService = MapDosProtectionService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (dosProtectionService.allowed((HttpServletRequest) request)) {
            chain.doFilter(request, response);
            return;
        }
        ((HttpServletResponse) response).sendError(429);
    }

    @Override
    public void destroy() {
    }
}
