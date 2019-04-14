package com.es.phoneshop.model.product.dao.order;

public enum PaymentMethod {
    MONEY("Money"),
    CREDIT_CARD("Credit card"),
    UNKNOWN("Unknown");

    private final String name;

    PaymentMethod(String name) {
        this.name = name;
    }

    public static PaymentMethod identify(String paymentMethod) {
        switch (paymentMethod.trim().toLowerCase()) {
            case "money":
                return MONEY;
            case "credit card":
                return CREDIT_CARD;
            default:
                return UNKNOWN;
        }
    }

    public String getName() {
        return name;
    }
}
