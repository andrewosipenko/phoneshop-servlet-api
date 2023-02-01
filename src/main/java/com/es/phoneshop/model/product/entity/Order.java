package com.es.phoneshop.model.product.entity;

import com.es.phoneshop.model.product.entity.Cart;
import com.es.phoneshop.model.product.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order extends Cart {
    private String secureId;
    private BigDecimal subTotalCost;
    private BigDecimal deliveryCost;
    private CustomerInfo customerInfo;

    public class CustomerInfo {
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private LocalDate deliveryDate;
        private String deliveryAddress;
        private PaymentMethod paymentMethod;

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

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
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
    }

    public BigDecimal getSubTotalCost() {
        return subTotalCost;
    }

    public void setSubTotalCost(BigDecimal subTotalCost) {
        this.subTotalCost = subTotalCost;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Order() {
        subTotalCost = super.getTotalCost();
        customerInfo = new CustomerInfo();
    }

    public String getSecureId() {
        return secureId;
    }

    public void setSecureId(String secureId) {
        this.secureId = secureId;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }
}
