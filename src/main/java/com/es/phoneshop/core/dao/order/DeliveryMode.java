package com.es.phoneshop.core.dao.order;

import java.math.BigDecimal;

public enum DeliveryMode {
    COURIER("Courier", BigDecimal.valueOf(10)),
    STORE_PICKUP("Store pickup", BigDecimal.ZERO);

    private final String name;
    private final BigDecimal price;

    DeliveryMode(String name, BigDecimal price) {
        this.price = price;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
