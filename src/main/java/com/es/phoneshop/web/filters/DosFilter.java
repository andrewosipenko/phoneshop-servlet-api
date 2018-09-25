package com.es.phoneshop.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class DosFilter implements Filter {
    private final int MAX_COUNT = 100;
    private final int INTERVAL_SECONDS = 5000;

    private Map<String, Integer> requestCountMap = Collections.synchronizedMap(new HashMap());
    private Map<String, Long> requestTimeMap = Collections.synchronizedMap(new HashMap<>());


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String address = request.getRemoteAddr();
        Integer count = requestCountMap.get(address);
        if (count == null) {
            requestTimeMap.put(address, System.currentTimeMillis());
            count = 1;
        } else {
            count += 1;
        }
        requestCountMap.put(address, count);
        if (count > MAX_COUNT) {
            if (System.currentTimeMillis() - requestTimeMap.get(address) < INTERVAL_SECONDS) {
                ((HttpServletResponse) response).sendError(429);
            }
        } else {
            if (System.currentTimeMillis() - requestTimeMap.get(address) > INTERVAL_SECONDS) {
                requestCountMap.put(address, null);
                requestTimeMap.put(address, System.currentTimeMillis());
                chain.doFilter(request, response);
            } else{
            chain.doFilter(request, response);
        }
    }

}

    @Override
    public void destroy() {
    }

}
