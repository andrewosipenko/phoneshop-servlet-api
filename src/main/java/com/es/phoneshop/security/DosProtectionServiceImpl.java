package com.es.phoneshop.security;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public enum DosProtectionServiceImpl implements DosProtectionService {

    INSTANCE;

    private final Object lock = new Object();

    private static final long THRESHOLD = 10;

    private final Map<String, AtomicLong> countMap = new ConcurrentHashMap<>();

    {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(countMap::clear, 1, TimeUnit.MINUTES);
    }

    @Override
    public boolean isAllowed(String ip) {
        //is syncronized on string.intern() ok?

        AtomicLong count = countMap.get(ip);
        if(count == null) {
            synchronized (lock) {
                if(count == null) {
                    count = new AtomicLong(1L);
                }
            }
        } else {
            if(count.longValue() > THRESHOLD) {
                return false;
            }
            count.incrementAndGet();
        }
        countMap.put(ip, count);
        return true;
    }
}
