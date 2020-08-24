package com.es.phoneshop.model.order.dao;

import com.es.phoneshop.model.GenericArrayListDao;
import com.es.phoneshop.model.order.entity.Order;

public class ArrayListOrderDao extends GenericArrayListDao<Order>  {

    private static class SingletonHelper {
        private static final ArrayListOrderDao INSTANCE = new ArrayListOrderDao();
    }

    public static ArrayListOrderDao getInstance() {
        return SingletonHelper.INSTANCE;
    }


}
