package com.es.phoneshop.web.firstRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultFirstRequestService implements FirstRequestService {
    private static FirstRequestService firstRequestService;

    Map<String, Integer> requestCountMap = new ConcurrentHashMap<>();

    private DefaultFirstRequestService() {
    }

    public static FirstRequestService getInstance() {
        if (firstRequestService == null) {
            synchronized (DefaultFirstRequestService.class) {
                if (firstRequestService == null) {
                    firstRequestService = new DefaultFirstRequestService();
                }
            }
        }
        return firstRequestService;
    }

    @Override
    public boolean isFirstRequest(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        Integer count = requestCountMap.get(ip);
        if (count == null) {
            count = 0;
            requestCountMap.put(ip, count + 1);
            return true;
        }
        return false;
    }
}

