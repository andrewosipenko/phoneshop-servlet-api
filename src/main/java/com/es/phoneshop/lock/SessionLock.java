package com.es.phoneshop.lock;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public interface SessionLock {
    ReentrantReadWriteLock getSessionLock(HttpServletRequest request);
}
