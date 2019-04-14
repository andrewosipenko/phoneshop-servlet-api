package com.es.phoneshop.model.product.dao.order;

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

    public static DeliveryMode identify(String deliveryMode) {
        switch (deliveryMode.trim().toLowerCase()) {
            case "courier":
                return COURIER;
            case "store pickup":
            case "store_pickup":
                return STORE_PICKUP;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
