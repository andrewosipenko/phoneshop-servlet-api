package com.es.phoneshop.model.order;

import com.es.phoneshop.model.DaoEntity;
import com.es.phoneshop.enumeration.PaymentMethod;
import com.es.phoneshop.model.cart.Cart;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Objects;

public class Order extends Cart implements DaoEntity<Order> {
    private Long id;
    private String secureId;
    private BigDecimal subtotal;
    private BigDecimal deliveryCost;
    private Currency currency;


    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate deliveryDate;
    private String deliveryAddress;
    private PaymentMethod paymentMethod;

    public Order() {
        currency = Currency.getInstance("USD");
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(Currency currency) {
        this.currency = currency;
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

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setSecureId(String secureId) {
        this.secureId = secureId;
    }

    public String getSecureId() {
        return secureId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(subtotal, order.subtotal) && Objects.equals(deliveryCost, order.deliveryCost) && Objects.equals(currency, order.currency) && Objects.equals(firstName, order.firstName) && Objects.equals(lastName, order.lastName) && Objects.equals(phone, order.phone) && Objects.equals(deliveryDate, order.deliveryDate) && Objects.equals(deliveryAddress, order.deliveryAddress) && paymentMethod == order.paymentMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtotal, deliveryCost, currency, firstName, lastName, phone, deliveryDate, deliveryAddress, paymentMethod);
    }
}
