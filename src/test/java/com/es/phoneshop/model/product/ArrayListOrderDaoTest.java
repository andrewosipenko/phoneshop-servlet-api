package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.DefaultCartService;
import com.es.phoneshop.model.product.exceptions.ItemNotFindException;
import com.es.phoneshop.model.product.exceptions.StockException;
import com.es.phoneshop.model.product.order.*;
import com.es.phoneshop.model.product.productdao.ArrayListProductDao;
import com.es.phoneshop.model.product.productdao.ProductBuilderImpl;
import com.es.phoneshop.model.product.productdao.ProductDao;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ArrayListOrderDaoTest {
    private OrderDao orderDao;
    private OrderService orderService;
    private Cart cart;
    private CartService cartService;

    @Before
    public void setup() {
        cart = new Cart();
        cartService = DefaultCartService.getInstance();
        orderDao = ArrayListOrderDao.getInstance();
        orderService = DefaultOrderService.getInstance();
        ProductDao productDao = ArrayListProductDao.getInstance();
        productDao.saveProduct(new ProductBuilderImpl().setId(0L).setCode("sgs").setDescription("Samsung Galaxy S").setPrice(new BigDecimal(100)).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg").build());
        productDao.saveProduct(new ProductBuilderImpl().setId(2L).setCode("sgs3").setDescription("Samsung Galaxy S III").setPrice(new BigDecimal(300)).setStock(5).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg").build());
        productDao.saveProduct(new ProductBuilderImpl().setId(3L).setCode("iphone").setDescription("Apple iPhone").setPrice(new BigDecimal(200)).setStock(10).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg").build());
        productDao.saveProduct(new ProductBuilderImpl().setId(4L).setCode("iphone6").setDescription("Apple iPhone 6").setPrice(new BigDecimal(1000)).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg").build());
        productDao.saveProduct(new ProductBuilderImpl().setId(5L).setCode("htces4g").setDescription("HTC EVO Shift 4G").setPrice(new BigDecimal(320)).setStock(3).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg").build());
        productDao.saveProduct(new ProductBuilderImpl().setId(6L).setCode("sec901").setDescription("Sony Ericsson C901").setPrice(new BigDecimal(420)).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg").build());
        productDao.saveProduct(new ProductBuilderImpl().setId(7L).setCode("xperiaxz").setDescription("Sony Xperia XZ").setPrice(new BigDecimal(120)).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg").build());
        productDao.saveProduct(new ProductBuilderImpl().setId(8L).setCode("nokia3310").setDescription("Nokia 3310").setPrice(new BigDecimal(70)).setStock(100).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg").build());
        productDao.saveProduct(new ProductBuilderImpl().setId(9L).setCode("palmp").setDescription("Palm Pixi").setPrice(new BigDecimal(170)).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg").build());
        productDao.saveProduct(new ProductBuilderImpl().setId(10L).setCode("simc56").setDescription("Siemens C56").setPrice(new BigDecimal(70)).setStock(20).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg").build());
        productDao.saveProduct(new ProductBuilderImpl().setId(11L).setCode("simc61").setDescription("Siemens C61").setPrice(new BigDecimal(80)).setStock(30).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg").build());
        productDao.saveProduct(new ProductBuilderImpl().setId(12L).setCode("simsxg75").setDescription("Siemens SXG75").setPrice(new BigDecimal(150)).setStock(40).setImageUrl("https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg").build());
    }

    @Test
    public void testSaveOrder() throws StockException {
        cartService.addToCart(cart, 0L, 1);
        Order testOrder = orderService.getOrder(cart);
        orderDao.saveOrder(testOrder);
        assertEquals(testOrder, orderDao.getOrder(testOrder.getId()));
    }

    @Test
    public void testSaveOrderError() throws StockException {
        cartService.addToCart(cart, 0L, 1);
        Order testOrder = new Order();
        testOrder.setId(1L);
        orderDao.saveOrder(testOrder);
        assertEquals(testOrder, orderDao.getOrder(testOrder.getId()));
    }

    @Test
    public void testGetOrder() {
        Order order = new Order();
        order.setSubtotalPrice(new BigDecimal(100));
        orderDao.saveOrder(order);
        Order testOrder = orderDao.getOrder(order.getId());
        assertEquals(testOrder, order);
    }

    @Test
    public void testGetOrderError() {
        try {
            orderDao.getOrder(0L);
            fail("Expected ItemNotFindException");
        } catch (ItemNotFindException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }

    @Test
    public void testGetOrderBySecureId() {
        Order order = new Order();
        order.setSecureId("1");
        orderDao.saveOrder(order);
        Order testOrder = orderDao.getOrderBySecureId("1");
        assertEquals(testOrder, order);
    }

    @Test
    public void testDeleteOrder() {
        Order order = new Order();
        order.setSubtotalPrice(new BigDecimal(100));
        orderDao.saveOrder(order);
        orderDao.deleteOrder(0L);
        try {
            orderDao.getOrder(0L);
            fail("Expected ItemNotFindException");
        } catch (ItemNotFindException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }

    @Test
    public void testDeleteOrderError() {
        try {
            orderDao.deleteOrder(0L);
            fail("Expected ItemNotFindException");
        } catch (ItemNotFindException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }
}