package com.es.phoneshop.web.security;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapDosProtectionService implements DosProtectionService {
    private static final int MAX_REQUEST_COUNT = 20;
    private static DosProtectionService dosProtectionService;

    Map<String, Integer> requestCountMap = new ConcurrentHashMap<>();
    Map<String, Long> requestTimeMap = new ConcurrentHashMap<>();

    private MapDosProtectionService() {
    }

    public static DosProtectionService getInstance() {
        if (dosProtectionService == null) {
            synchronized (MapDosProtectionService.class) {
                if (dosProtectionService == null) {
                    dosProtectionService = new MapDosProtectionService();
                }
            }
        }
        return dosProtectionService;
    }

    @Override
    public boolean allowed(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        Long timeNow = System.currentTimeMillis();
        Long lastRequestTime = requestTimeMap.get(ip);
        if (lastRequestTime != null && timeNow - lastRequestTime > 60000) {
            requestTimeMap.remove(ip);
            requestCountMap.remove(ip);
        }
        Integer count = requestCountMap.get(ip);
        if (count == null) {
            timeNow = System.currentTimeMillis();
            count = 0;
        } else if (count > MAX_REQUEST_COUNT) {
            return false;
        }
        requestCountMap.put(ip, count + 1);
        requestTimeMap.put(ip, timeNow);
        return true;
    }
}
