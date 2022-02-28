package com.es.phoneshop.security;


import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultDosProtectionService implements DosProtectionService {
    private static DosProtectionService instance;
    private static final Object mutex = new Object();
    private static final long THRESHOLD = 50;
    private Map<String, TimeCountPair> countMap;

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

        TimeCountPair timeCountPair =
                countMap.computeIfAbsent(ip,k -> new TimeCountPair(LocalTime.now(), 0L));

        timeCountPair.getLock().lock();
        try {
            if (LocalTime.now().toSecondOfDay() - timeCountPair.getTime().toSecondOfDay() >= 60) {
                timeCountPair.reset();
                return true;
            } else {
                if (timeCountPair.getCount() + 1 > THRESHOLD) {
                    return false;
                } else {
                    timeCountPair.increaseCount();
                    return true;
                }
            }
        }finally {
            timeCountPair.getLock().unlock();
        }
    }
}

class TimeCountPair {
    private LocalTime time;
    private Long count;
    private ReentrantLock lock;

    public TimeCountPair(LocalTime time, Long count) {
        this.time = time;
        this.count = count;
        lock = new ReentrantLock();
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public ReentrantLock getLock(){
        return lock;
    }

    public void increaseCount() {
        this.count++;
    }

    public void reset() {
        this.time = LocalTime.now();
        this.count = 0L;
    }
}
