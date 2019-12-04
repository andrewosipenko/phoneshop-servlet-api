package com.es.phoneshop.model.order;

public interface OrderDao {
    void saveOrder(Order order);

    Order getOrder(String secureId);
}
