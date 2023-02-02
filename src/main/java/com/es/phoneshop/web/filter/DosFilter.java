package com.es.phoneshop.web.filter;

import com.es.phoneshop.security.DefaultDosProtectionService;
import com.es.phoneshop.security.DosProtectionService;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {
    private DosProtectionService dosProtectionService;

    @Override
    public void init(FilterConfig filterConfig) {
        dosProtectionService = DefaultDosProtectionService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(dosProtectionService.isAllowed(request.getRemoteAddr())) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse)response).setStatus(429);
        }
    }

    @Override
    public void destroy() {

    }
}
