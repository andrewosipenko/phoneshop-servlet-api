package com.es.phoneshop.web.helper;

import java.util.Arrays;
import java.util.Optional;

public enum Error {
    PARSE_ERROR("nfe"),
    OUT_OF_STOCK("oos"),
    UNKNOWN("unkn");

    private final String errorCode;

    Error(String errorCode) {
        this.errorCode = errorCode;
    }

    static Error identify(String errorCode) {
        Optional<Error> optionalError = Arrays.stream(Error.values())
                .filter(error -> error.errorCode.equals(errorCode))
                .findFirst();
        return optionalError.orElse(Error.UNKNOWN);
    }

    public String getErrorCode() {
        return errorCode;
    }
}