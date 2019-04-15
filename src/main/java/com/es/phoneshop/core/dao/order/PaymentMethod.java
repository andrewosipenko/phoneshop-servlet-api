package com.es.phoneshop.core.dao.order;

public enum PaymentMethod {
    MONEY("Money"),
    CREDIT_CARD("Credit card");

    private final String name;

    PaymentMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
