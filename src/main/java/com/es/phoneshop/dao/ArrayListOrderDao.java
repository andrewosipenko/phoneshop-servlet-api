package com.es.phoneshop.dao;

import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class ArrayListOrderDao extends GenericDao<Order> implements OrderDao {
    private static OrderDao instance;

    public static synchronized OrderDao getInstance() {
        if (instance == null) {
            instance = new ArrayListOrderDao();
        }
        return instance;
    }

    private ArrayListOrderDao() {
    }

    @Override
    public Order findBySecureId(String id) throws OrderNotFoundException {
        if (id == null) {
            throw new OrderNotFoundException(null);
        }

        return items.stream()
                .filter(order -> id.equals(order.getSecureId()))
                .findAny()
                .orElseThrow(new OrderNotFoundException());
    }

    public void setOrders(List<Order> orders) {
        this.items = orders;
    }

    public void setMaxId(long maxId) {
        this.maxId = maxId;
    }

    @Override
    protected Supplier<? extends NoSuchElementException> getItemNotFoundExceptionSupplier(Long id) {
        OrderNotFoundException exception = new OrderNotFoundException(id);
        return exception::get;
    }
}
