package com.es.phoneshop.web.servlet;

import com.es.phoneshop.dao.ArrayListOrderDao;
import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.enumeration.PaymentMethod;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderOverviewPageServletTest {
    private Order order;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private OrderOverviewPageServlet servlet = new OrderOverviewPageServlet();

    @Before
    public void setup() throws ServletException {
        servlet.init(servlet.getServletConfig());
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn("/" + 1);

        OrderDao orderDao = ArrayListOrderDao.getInstance();
        ProductDao productDao = ArrayListProductDao.getInstance();
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);

        order = createNewOrder(1L, productDao);
        order.setItems(cartItems);
        order.setId(1L);
        order.setSecureId("1");

        orderDao.save(order);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute("order", order);
    }

    private Order createNewOrder(Long orderId, ProductDao productDao) {
        Order order = new Order();
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem(productDao.findById(1L), 1);
        cartItems.add(cartItem);
        order.setItems(cartItems);
        if (orderId != null) {
            order.setId(orderId);
        }
        order.setSecureId(UUID.randomUUID().toString());
        order.setPhone("+375447626615");
        order.setFirstName("Anton");
        order.setLastName("Ahinski");
        order.setDeliveryDate(LocalDate.of(2023, 10, 10));
        order.setDeliveryAddress("sdjgak");
        order.setPaymentMethod(PaymentMethod.CASH);
        return order;
    }
}