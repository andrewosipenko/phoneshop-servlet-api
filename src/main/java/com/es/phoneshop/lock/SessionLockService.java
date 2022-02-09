package com.es.phoneshop.lock;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SessionLockService implements SessionLock {
    private static final String LOCK_SESSION_ATTRIBUTE = SessionLockService.class.getName() + ".lock";
    private static SessionLock instance;
    private static final Object mutex = new Object();
    private final ReadWriteLock rwLock;


    private SessionLockService(){
        rwLock = new ReentrantReadWriteLock();
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
    public ReentrantReadWriteLock getSessionLock(HttpServletRequest request) {
        rwLock.readLock().lock();
        try {
            ReentrantReadWriteLock lock = (ReentrantReadWriteLock) request.getSession().getAttribute(LOCK_SESSION_ATTRIBUTE);
            if (lock == null) {
                request.getSession().setAttribute(LOCK_SESSION_ATTRIBUTE, lock = new ReentrantReadWriteLock());
            }
            return lock;
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
