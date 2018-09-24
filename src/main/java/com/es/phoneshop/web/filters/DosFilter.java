package com.es.phoneshop.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class DosFilter implements Filter {
    private static final int MAX_REQUESTS = 10;
    private static final int INTERVAL = 5;
    private AtomicInteger count;
    private Map<String, AtomicInteger> requestCountMap = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void init(FilterConfig filterConfig) {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                requestCountMap.clear();
            }
        }, 0, INTERVAL*1000);
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String address = request.getRemoteAddr();
        System.out.println("Filtering " + address);
        count = requestCountMap.get(address);
        if (count == null) {
            count = new AtomicInteger(1);
        } else {
            count.incrementAndGet();
        }
        requestCountMap.put(address, count);
        if (count.get() >= MAX_REQUESTS) {
            ((HttpServletResponse)response).sendError(429);
        } else {
            chain.doFilter(request, response);
        }
    }
    @Override
    public void destroy() {
    }
}