package com.es.phoneshop.security;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DefaultDosProtectionService implements DosProtectionService{
    private static final long THRESHOLD = 40;
    private static final int RESET_TIME = 1000 * 60;
    private Map<String, IpInfo> info;

    private Map<String, Long> countMap = new ConcurrentHashMap<>();
    private static class SingletonHelper {
        private static final DefaultDosProtectionService INSTANCE = new DefaultDosProtectionService();
    }

    public static DefaultDosProtectionService getInstance() {
        return DefaultDosProtectionService.SingletonHelper.INSTANCE;
    }

    private DefaultDosProtectionService(){
        info = new HashMap<>();
    }

    private static class IpInfo {
        private Long count;
        private long lastRequest;

        public IpInfo(Long count, long lastRequest) {
            this.count = count;
            this.lastRequest = lastRequest;
        }
    }

    @Override
    public boolean isAllowed(String ip) {
        info.putIfAbsent(ip, new IpInfo(1L, System.currentTimeMillis()));
        IpInfo ipInfo = info.get(ip);

        if (System.currentTimeMillis() - ipInfo.lastRequest > RESET_TIME) {
            ipInfo.count = 1L;
            ipInfo.lastRequest = System.currentTimeMillis();
        }

        if(ipInfo.count ==null){
            ipInfo.count = 1L;
        }else{
            if(ipInfo.count >THRESHOLD){
                return false;
            }
            ipInfo.count++;
        }
        countMap.put(ip, ipInfo.count);
        return true;
    }
}
