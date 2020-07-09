package com.es.phoneshop.dao;

import com.es.phoneshop.model.Order;

public interface OrderDao extends DefaultDao<Order> {
    Order getBySecretId(String secretKey);
}
