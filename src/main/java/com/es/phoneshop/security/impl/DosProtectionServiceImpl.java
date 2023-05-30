package com.es.phoneshop.security.impl;

import com.es.phoneshop.security.DosProtectionService;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DosProtectionServiceImpl implements DosProtectionService {
    private static final Long THRESHOLDS = 20L;
    private static final Long DURATION = 60000L;
    private class ThresholdPair {
        protected Long lastThreshold;
        private Long count;

        public ThresholdPair(Long lastThreshold, Long count) {
            this.lastThreshold = lastThreshold;
            this.count = count;
        }

        public Long getLastThreshold() {
            return lastThreshold;
        }

        public void setLastThreshold(Long lastThreshold) {
            this.lastThreshold = lastThreshold;
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }
    }
    protected static Map<String, ThresholdPair> requestMap = new ConcurrentHashMap();

    public static DosProtectionServiceImpl getInstance() {
        return DosProtectionServiceImpl.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final DosProtectionServiceImpl INSTANCE = new DosProtectionServiceImpl();
    }

    @Override
    public boolean isAllowed(String ip) {
        ThresholdPair pair = requestMap.get(ip);
        if (Optional.ofNullable(pair).isEmpty()) {
            requestMap.put(ip, new ThresholdPair(System.currentTimeMillis(), 1L));
        } else {
            if (System.currentTimeMillis() - pair.getLastThreshold() > DURATION) {
                pair.setCount(1L);
                pair.setLastThreshold(System.currentTimeMillis());
            } else {
                if (pair.getCount() > THRESHOLDS) {
                    return false;
                }
                pair.setCount(pair.getCount() + 1L);
            }
        }
        return true;
    }
}
