package com.es.phoneshop.model.order;

import com.es.phoneshop.model.Product;

public class OrderItem extends AbstractOrderItem {
    public OrderItem(Product product, int quantity) {
        super(product, quantity);
    }
}
