package com.es.phoneshop.lock;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.locks.ReentrantLock;

public interface SessionLock {
    ReentrantLock getSessionLock(HttpServletRequest request);
}
