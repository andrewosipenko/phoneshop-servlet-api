package com.es.phoneshop.model.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order extends AbstractOrder<OrderItem> implements Serializable {
    private String id;
    private String name;
    private String address;
    private String phone;
    private Integer totalCost;

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public Order(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public List<OrderItem> getCartItems() {
        return new ArrayList<>(orderItems);
    }
}
