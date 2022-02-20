package com.es.phoneshop.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultDosProtectionService implements DosProtectionService {
    private static DosProtectionService instance;
    private static final Object mutex = new Object();
    private static final long THRESHOLD = 50;
    private Map<String, Long> countMap;

    public static DosProtectionService getInstance() {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new DefaultDosProtectionService();
                }
            }
        }
        return instance;
    }

    private DefaultDosProtectionService() {
        countMap = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isAllowed(String ip) {
        Long count = countMap.get(ip);
        if (count == null) {
            count = 1L;
        } else {
            if (count > THRESHOLD) {
                return false;
            } else {
                count++;
            }
        }
        countMap.put(ip, count);
        return true;
    }
}
