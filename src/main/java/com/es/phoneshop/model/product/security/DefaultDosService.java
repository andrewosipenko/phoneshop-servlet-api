package com.es.phoneshop.model.product.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultDosService implements DosService {

    public static final int THRESHOLD = 20;
    public static final long UPDATE_LATENCY = 6;
    private long lastMinuteTiming = System.currentTimeMillis();
    Map<String, Long> countIpRequests = new ConcurrentHashMap<>();

    private static volatile DefaultDosService instance;

    private DefaultDosService() {
    }

    public static DefaultDosService getInstance() {
        DefaultDosService result = instance;
        if (result != null) {
            return result;
        }
        synchronized (DefaultDosService.class) {
            if (instance == null) {
                instance = new DefaultDosService();
            }
            return instance;
        }
    }

    @Override
    public boolean isAllowed(String ip) {
        Long countRequests = countIpRequests.get(ip);
        if (countRequests == null || (System.currentTimeMillis() - lastMinuteTiming > UPDATE_LATENCY)) {
            countRequests = 1L;
            lastMinuteTiming = System.currentTimeMillis();
        } else {
            if (countRequests > THRESHOLD) {
                return false;
            }
            countRequests++;
        }
        countIpRequests.put(ip, countRequests);
        return true;
    }
}