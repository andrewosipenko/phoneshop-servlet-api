package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.CartItem;

import java.math.BigDecimal;
import java.util.List;

public class Order extends CustomerInfo {
    private String secureId;

    private BigDecimal deliveryCost;
    private BigDecimal subtotalCost;

    private PaymentMethod paymentMethod;

    public Order() {
    }

    public Order(List<CartItem> cartItems) {
        super(cartItems);
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public BigDecimal getSubtotalCost() {
        return subtotalCost;
    }

    public void setSubtotalCost(BigDecimal subtotalsCost) {
        this.subtotalCost = subtotalsCost;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getSecureId() {
        return secureId;
    }

    public void setSecureId(String secureId) {
        this.secureId = secureId;
    }
}
