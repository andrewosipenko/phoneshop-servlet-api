package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

public class Order extends Cart {
    private Long id;
    private String name;
    private String deliveryAddress;
    private String phone;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
