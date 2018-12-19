package com.es.phoneshop.web;

import com.es.phoneshop.CartService.Cart;
import com.es.phoneshop.order.Order;
import com.es.phoneshop.order.OrderService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.es.phoneshop.projectConstants.Constants.CART;

public class CheckOutServlet extends HttpServlet {

    private OrderService orderService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderService = OrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/pages/checkOut.jsp").forward(request, responce);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute(CART);

        if(cart != null){
            Order order = new Order(request.getParameter("firstName"), request.getParameter("lastName"), request.getParameter("phone"), request.getParameter("adress"), cart);
            if(orderService.validCheck(order)){
                orderService.addOrder(order);
                request.setAttribute("firstName",order.getName());
                request.setAttribute("lastName", order.getLastName());
                request.setAttribute("phone",order.getPhoneNumber());
                request.setAttribute("adress", order.getDeloveryAdress());
                String path = request.getContextPath() + "/order/overview/" + order.getUniqueAdress();
                responce.sendRedirect(path);
            }

            else{
                request.getRequestDispatcher("WEB-INF/pages/checkOut.jsp").forward(request, responce);
            }
        }
    }
}
