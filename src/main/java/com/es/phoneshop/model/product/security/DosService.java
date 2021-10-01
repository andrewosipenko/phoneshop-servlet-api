package com.es.phoneshop.model.product.security;

public interface DosService {
    boolean isAllowed(String ip);
}