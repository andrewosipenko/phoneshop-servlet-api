package com.es.phoneshop.web.utils;

public class UrlParamsSection {
    private StringBuilder result;

    public UrlParamsSection() {

    }

    public UrlParamsSection appendParam(String key, String value) {
        if(result == null) {
            result = new StringBuilder("?" + key + "=" + value);
        } else {
            result.append("&").append(key).append("=").append(value);
        }
        return this;
    }

    public String build() {
        return result.toString();
    }
}
