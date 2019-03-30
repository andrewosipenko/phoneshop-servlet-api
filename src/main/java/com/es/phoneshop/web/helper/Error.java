package com.es.phoneshop.web.helper;

import java.util.Arrays;
import java.util.Optional;

public enum Error {
    PARSE_ERROR("nfe"),
    OUT_OF_STOCK("oos");

    private final String errorCode;

    Error(String errorCode) {
        this.errorCode = errorCode;
    }

    static Optional<Error> indentify(String errorCode) {
        return Arrays.stream(Error.values())
                .filter(error -> error.errorCode.equals(errorCode))
                .findFirst();
    }

    public String getErrorCode() {
        return errorCode;
    }
}