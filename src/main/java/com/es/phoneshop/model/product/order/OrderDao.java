package com.es.phoneshop.model.product.order;

import com.es.phoneshop.model.product.exceptions.ItemNotFindException;

public interface OrderDao {
    void deleteOrder(Long id) throws ItemNotFindException;

    void saveOrder(Order order) throws ItemNotFindException;

    Order getOrder(Long id) throws ItemNotFindException;

    Order getOrderBySecureId(String secureId) throws ItemNotFindException;
}