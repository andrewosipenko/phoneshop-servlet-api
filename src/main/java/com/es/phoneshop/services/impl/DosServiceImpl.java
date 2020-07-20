package com.es.phoneshop.services.impl;

import com.es.phoneshop.services.DosService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DosServiceImpl implements DosService {
    private static final Integer AMOUNT_QUERIES_PER_TIME = 20;
    private static final Integer CHECK_TIME_IN_SECONDS = 60;
    private static final Integer BAN_TIME_IN_SECONDS = 120;
    HashMap<String, LocalDateTime> blockedIp = new HashMap<>();
    HashMap<String, List<IpInfo>> stringListHashMap = new HashMap<>();

    public static class IpInfo {
        private LocalDateTime timeToQuery;

        public IpInfo(LocalDateTime timeToQuery) {
            this.timeToQuery = timeToQuery;
        }
    }

    private void toBlockByIp(String ip) {
        blockedIp.put(ip, LocalDateTime.now());
    }

    private boolean isBanned(String ip) {
        if (blockedIp.get(ip) != null) {
            if (LocalDateTime.now().minusSeconds(BAN_TIME_IN_SECONDS).isAfter(blockedIp.get(ip))) {
                blockedIp.remove(ip);
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAllowed(String ip) {
        if (!isBanned(ip)) {
            stringListHashMap.putIfAbsent(ip, new ArrayList<>());
            int countQueries = 0;
            List<IpInfo> ipInfos = stringListHashMap.get(ip);
            for (int i = ipInfos.size() - 1; i >= 0; i--) {
                if (ipInfos.get(i).timeToQuery.isBefore(LocalDateTime.now().minusSeconds(CHECK_TIME_IN_SECONDS))) {
                    break;
                }
                countQueries++;
            }
            if (countQueries < AMOUNT_QUERIES_PER_TIME) {
                ipInfos.add(new IpInfo(LocalDateTime.now()));
                return true;
            } else {
                toBlockByIp(ip);
            }
        }
        return false;
    }
}
