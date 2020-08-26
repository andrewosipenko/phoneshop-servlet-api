package com.es.phoneshop.model.order.dao;

import com.es.phoneshop.model.GenericArrayListDao;
import com.es.phoneshop.model.order.entity.Order;

import java.util.Optional;

public class ArrayListOrderDao extends GenericArrayListDao<Order> implements OrderDAO {

    private static class SingletonHelper {

        private static final ArrayListOrderDao INSTANCE = new ArrayListOrderDao();
    }
    public static ArrayListOrderDao getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public Optional<Order> getOrderBySecureId(String secureId) {
        readLock.lock();
        try {
            return items.stream()
                    .filter(entity -> secureId.equals(entity.getSecureId()))
                    .findAny();
        } finally {
            readLock.unlock();
        }
    }


}
