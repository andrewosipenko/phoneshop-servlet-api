package com.es.phoneshop.lock;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SessionLockService implements SessionLock {
    private static final String LOCK_SESSION_ATTRIBUTE = SessionLockService.class.getName() + ".lock";
    private static SessionLock instance;
    private static final Object mutex = new Object();
    private final Lock lock;


    private SessionLockService(){
        this.lock = new ReentrantLock();
    }


    public static SessionLock getInstance() {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new SessionLockService();
                }
            }
        }
        return instance;
    }

    @Override
    public ReentrantLock getSessionLock(HttpServletRequest request) {
        this.lock.lock();
        try {
            ReentrantLock lock = (ReentrantLock) request.getSession().getAttribute(LOCK_SESSION_ATTRIBUTE);
            if (lock == null) {
                request.getSession().setAttribute(LOCK_SESSION_ATTRIBUTE, lock = new ReentrantLock());
            }
            return lock;
        } finally {
            this.lock.unlock();
        }
    }
}
