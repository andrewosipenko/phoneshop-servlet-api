package com.es.phoneshop.model.product.security.impl;

import com.es.phoneshop.model.product.security.DosProtectionService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultDosProtectionService implements DosProtectionService {
    private static volatile DefaultDosProtectionService instance;

    private static final long MAX_AMOUNT_OF_REQUESTS_FROM_SINGLE_IP = 20;

    private final long MINUTE = 60000000000L;

    private ThreadLocal<Long> clockBeginTime;

    private Map<String, Long> requestsMap;

    public static DefaultDosProtectionService getInstance() {
        DefaultDosProtectionService localInstance = instance;
        if (localInstance == null) {
            synchronized (DefaultDosProtectionService.class) {
                localInstance = instance;
                if (localInstance == null)
                    instance = localInstance = new DefaultDosProtectionService();
            }
        }
        return localInstance;
    }

    private DefaultDosProtectionService() {
        requestsMap = new ConcurrentHashMap<>();
        clockBeginTime = ThreadLocal.withInitial(System::nanoTime);
    }

    @Override
    public boolean isAllowed(String ipAddress) {
        Long count = requestsMap.get(ipAddress);
        if (count == null) {
            count = 0L;
        } else if (count > MAX_AMOUNT_OF_REQUESTS_FROM_SINGLE_IP) {
            if (System.nanoTime() - clockBeginTime.get() < MINUTE) {
                return false;
            } else {
                requestsMap.put(ipAddress, 0L);
                clockBeginTime.set(System.nanoTime());
                return true;
            }
        }
        requestsMap.put(ipAddress, ++count);
        return true;
    }

    public Map<String, Long> getRequestsMap() {
        return requestsMap;
    }
}
