package com.es.phoneshop.security;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultDosProtectionService implements DosProtectionService {
    private static final long THRESHOLD = 20;
    private volatile Date reset;

    private Map<String, Long> countMap = new ConcurrentHashMap<>();

    private DefaultDosProtectionService() {
        reset = new Date();
    }

    private static DefaultDosProtectionService instance;

    public static synchronized DefaultDosProtectionService getInstance() {
        if (instance == null) {
            instance = new DefaultDosProtectionService();
        }
        return instance;
    }

    @Override
    public boolean isAllowed(String ip) {
        resetIfNecessary();

        Long count = countMap.get(ip);

        if (count == null) {
            count = 1L;
        } else {
            if (count > THRESHOLD) {
                return false;
            }
            count++;
        }

        countMap.put(ip, count);
        return true;
    }

    private void resetIfNecessary() {
        Date date = new Date();

        if(date.getTime() - reset.getTime() > 60000) {
            reset = date;
            countMap.clear();
        }
    }
}
