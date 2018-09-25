package com.es.phoneshop.model.order;

import com.es.phoneshop.model.Product;

import java.io.Serializable;

public class OrderItem extends AbstractOrderItem implements Serializable {
    public OrderItem(Product product, int quantity) {
        super(product, quantity);
    }

    public OrderItem() {
        super();
    }
}
