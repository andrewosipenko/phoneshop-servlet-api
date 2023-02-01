package com.es.phoneshop.service;

import com.es.phoneshop.model.product.entity.Cart;
import com.es.phoneshop.model.product.entity.CartItem;
import com.es.phoneshop.model.product.entity.Product;
import com.es.phoneshop.model.product.entity.Order;
import com.es.phoneshop.model.product.service.OrderService;
import com.es.phoneshop.model.product.service.impl.OrderServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImplTest {

    private OrderService orderService;


    @Before
    public void init() {
        orderService = OrderServiceImpl.getInstance();
    }

    @Test
    public void testCreatingOrderFromCart() {
        Cart cart = new Cart();
        cart.setId(123L);
        cart.setTotalCost(new BigDecimal(100));
        cart.setTotalItems(3);

        List<CartItem> testList = new ArrayList<>();
        testList.add(new CartItem(new Product(), 2));
        cart.setItems(testList);

        Order order = orderService.createOrderFromCart(cart);
        Assert.assertTrue(
                order.getDeliveryCost().equals(BigDecimal.TEN) &&
                        order.getSubTotalCost().equals(cart.getTotalCost()) &&
                        order.getTotalItems() == cart.getTotalItems());
    }

    @Test
    public void testCloningObjectsWhileCreatingOrderFromCart() {
        Cart cart = new Cart();
        cart.setId(123L);
        cart.setTotalCost(new BigDecimal(100));
        cart.setTotalItems(1);

        List<CartItem> testList = new ArrayList<>();
        testList.add(new CartItem(new Product(), 2));
        cart.setItems(testList);

        Order order = orderService.createOrderFromCart(cart);
        for (int i = 0; i < order.getTotalItems(); i++) {
            Assert.assertEquals(order.getItems().get(i).getProduct(), cart.getItems().get(i).getProduct());
            Assert.assertEquals(order.getItems().get(i).getQuantity(), cart.getItems().get(i).getQuantity());
        }
    }
}
