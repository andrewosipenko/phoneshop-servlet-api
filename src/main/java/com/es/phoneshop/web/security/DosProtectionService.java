package com.es.phoneshop.web.security;

import javax.servlet.http.HttpServletRequest;

public interface DosProtectionService {
    boolean allowed(HttpServletRequest request);
}
