package com.es.phoneshop.model.product.order;

import com.es.phoneshop.model.product.cart.CartItem;
import com.es.phoneshop.model.product.enums.payment.PaymentMethod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Order implements Serializable {
    private String secureId = UUID.randomUUID().toString();
    private Long id;
    private List<CartItem> cartItems;
    private int totalQuantity;
    private BigDecimal subtotalPrice;
    private BigDecimal deliveryPrice;
    private BigDecimal totalPrice;
    private String phone;
    private String firstName;
    private String lastName;
    private String deliveryDate;
    private String deliveryAddress;
    private PaymentMethod paymentMethod;

    public void setSecureId(String secureId) {
        this.secureId = secureId;
    }

    public String getSecureId() {
        return secureId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(BigDecimal subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
    public String toString() {
        return "Order{" +
                "secureId='" + secureId + '\'' +
                ", id=" + id +
                ", cartItems=" + cartItems +
                ", totalQuantity=" + totalQuantity +
                ", subtotalPrice=" + subtotalPrice +
                ", deliveryPrice=" + deliveryPrice +
                ", totalPrice=" + totalPrice +
                ", phone='" + phone + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", paymentMethod=" + paymentMethod +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (totalQuantity != order.totalQuantity) return false;
        if (secureId != null ? !secureId.equals(order.secureId) : order.secureId != null) return false;
        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (cartItems != null ? !cartItems.equals(order.cartItems) : order.cartItems != null) return false;
        if (subtotalPrice != null ? !subtotalPrice.equals(order.subtotalPrice) : order.subtotalPrice != null)
            return false;
        if (deliveryPrice != null ? !deliveryPrice.equals(order.deliveryPrice) : order.deliveryPrice != null)
            return false;
        if (totalPrice != null ? !totalPrice.equals(order.totalPrice) : order.totalPrice != null) return false;
        if (phone != null ? !phone.equals(order.phone) : order.phone != null) return false;
        if (firstName != null ? !firstName.equals(order.firstName) : order.firstName != null) return false;
        if (lastName != null ? !lastName.equals(order.lastName) : order.lastName != null) return false;
        if (deliveryDate != null ? !deliveryDate.equals(order.deliveryDate) : order.deliveryDate != null) return false;
        if (deliveryAddress != null ? !deliveryAddress.equals(order.deliveryAddress) : order.deliveryAddress != null)
            return false;
        return paymentMethod == order.paymentMethod;
    }

    @Override
    public int hashCode() {
        int result = secureId != null ? secureId.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (cartItems != null ? cartItems.hashCode() : 0);
        result = 31 * result + totalQuantity;
        result = 31 * result + (subtotalPrice != null ? subtotalPrice.hashCode() : 0);
        result = 31 * result + (deliveryPrice != null ? deliveryPrice.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (deliveryDate != null ? deliveryDate.hashCode() : 0);
        result = 31 * result + (deliveryAddress != null ? deliveryAddress.hashCode() : 0);
        result = 31 * result + (paymentMethod != null ? paymentMethod.hashCode() : 0);
        return result;
    }
}