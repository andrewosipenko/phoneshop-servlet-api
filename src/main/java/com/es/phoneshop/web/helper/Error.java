package com.es.phoneshop.web.helper;

import java.util.Arrays;
import java.util.Optional;

public enum Error {
    PARSE_ERROR("nfe", "Not a number!"),
    OUT_OF_STOCK("oos", "Not enough stock!"),
    UNKNOWN("unkn", "Unknown error!");

    private final String errorCode;
    private final String errorMessage;

    Error(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
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

    public String getErrorMessage() {
        return errorMessage;
    }
}