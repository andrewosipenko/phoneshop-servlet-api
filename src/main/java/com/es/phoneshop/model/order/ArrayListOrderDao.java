package com.es.phoneshop.model.order;


import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;

public class ArrayListOrderDao implements OrderDao{
    private static volatile ArrayListOrderDao instance;
    private ArrayList<Order> orders;
    private long maxId = 0;

    private ArrayListOrderDao() {

        orders = new ArrayList<>();

    }

    public static ArrayListOrderDao getInstance(){
        if(instance == null)
            synchronized (ArrayListOrderDao.class){
                if(instance == null)
                    instance = new ArrayListOrderDao();
            }
        return instance;
    }

    @Override
    public synchronized Order getOrder(Long id) {
        synchronized(orders) {
            return orders.stream()
                    .filter((order) -> order.getId().equals(id))
                    .findFirst()
                    .get();
        }
    }

    @Override
    public void save(Order order){
        Long id = order.getId();
        synchronized (orders){
            if(orderExist(id)){
                throw new RuntimeException("Order with id " + id + "already exists.");
            }else{
                order.setId(maxId++);
                orders.add(order);
            }
        }

    }

    private boolean orderExist(Long id){
        return orders.stream()
                .anyMatch(order -> order.getId().equals(id));
    }
}
