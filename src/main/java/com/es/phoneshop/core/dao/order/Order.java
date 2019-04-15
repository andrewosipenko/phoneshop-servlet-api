package com.es.phoneshop.core.dao.order;

import com.es.phoneshop.core.cart.Cart;

import java.io.Serializable;
import java.util.UUID;

public class Order implements Serializable {
    private Cart cart;
    private UUID id;
    private String firstName;
    private String lastName;
    private String phone;
    private String deliveryDate;
    private String deliveryAddress;
    private DeliveryMode deliveryMode;
    private PaymentMethod paymentMethod;

    public Order() {
    }

    public Order(Cart cart,
                 String firstName,
                 String lastName,
                 String phone,
                 String deliveryDate,
                 String deliveryAddress,
                 DeliveryMode deliveryMode,
                 PaymentMethod paymentMethod) {
        this.cart = new Cart(cart);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.deliveryDate = deliveryDate;
        this.deliveryAddress = deliveryAddress;
        this.deliveryMode = deliveryMode;
        this.paymentMethod = paymentMethod;
        this.id = UUID.randomUUID();
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public DeliveryMode getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(DeliveryMode deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public UUID getId() {
        return id;
    }

    protected void setId(UUID id) {
        this.id = id;
    }
}
