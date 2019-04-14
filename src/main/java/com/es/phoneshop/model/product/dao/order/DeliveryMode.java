package com.es.phoneshop.model.product.dao.order;

public enum DeliveryMode {
    COURIER("Courier"),
    STORE_PICKUP("Store pickup"),
    UNKNOWN("Unknown");

    private final String name;

    DeliveryMode(String name) {
        this.name = name;
    }

    public static DeliveryMode identify(String deliveryMode) {
        switch (deliveryMode.trim().toLowerCase()) {
            case "courier":
                return COURIER;
            case "store pickup":
                return STORE_PICKUP;
            default:
                return UNKNOWN;
        }
    }

    public String getName() {
        return name;
    }
}
