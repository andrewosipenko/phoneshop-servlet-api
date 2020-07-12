package com.es.phoneshop.enums;

import java.util.Arrays;

public enum PaymentMethod {
    MONEY("money", 1),
    CREDIT_CART("Credit cart", 0);

    private String name;
    private Integer id;

    PaymentMethod(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public static PaymentMethod getById(Integer id) {
        return Arrays.stream(PaymentMethod.values())
                .filter(paymentMethod -> paymentMethod.id.equals(id))
                .findFirst().orElseThrow(NoSuchFieldError::new);
    }

    public static PaymentMethod getByName(String name) {
        return Arrays.stream(PaymentMethod.values())
                .filter(paymentMethod -> paymentMethod.name.equals(name))
                .findFirst()
                .orElseThrow(NoSuchFieldError::new);
    }
}
