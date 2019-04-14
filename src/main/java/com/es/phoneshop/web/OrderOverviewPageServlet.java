package com.es.phoneshop.web;

import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.dao.order.ArrayListOrderDao;
import com.es.phoneshop.model.product.dao.order.Order;
import com.es.phoneshop.model.product.dao.order.OrderDao;
import com.es.phoneshop.model.product.exceptions.OrderNotFoundException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class OrderOverviewPageServlet extends HttpServlet {

    protected final String ORDER = "order";
    protected final String ID = "id";
    private OrderDao orderDao;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) {
        orderDao = ArrayListOrderDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = getOrderId(req);
        Optional<Order> order = orderDao.findById(uuid);
        req.setAttribute(ID, uuid.toString());
        if (order.isPresent()) {
            req.setAttribute(ORDER, order.get());
            req.getRequestDispatcher("/WEB-INF/pages/overview.jsp").forward(req, resp);
            cartService.clearCart(req);
        } else {
            throw new OrderNotFoundException();
        }
    }

    private UUID getOrderId(HttpServletRequest req) {
        String idFromPath = req.getPathInfo().substring(1);
        return UUID.fromString(idFromPath);
    }

    protected void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    protected void setCartService(CartService cartService) {
        this.cartService = cartService;
    }
}
