package com.es.phoneshop.web.filter;

import com.es.phoneshop.web.firstRequest.DefaultFirstRequestService;
import com.es.phoneshop.web.firstRequest.FirstRequestService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirstRequestFilter implements Filter {
    private FirstRequestService firstRequestService;

    @Override
    public void init(FilterConfig filterConfig) {
        firstRequestService = DefaultFirstRequestService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (firstRequestService.isFirstRequest((HttpServletRequest) request)) {
            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/welcomePage");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
