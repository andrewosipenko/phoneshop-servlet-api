package com.es.phoneshop.web;

import com.es.phoneshop.CartService.CartService;
import com.es.phoneshop.logic.ApplicationService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.es.phoneshop.projectConstants.Constants.*;

public class CartDeletePageServlet extends HttpServlet {
    private CartService cartService;
    private ApplicationService applicationLogic;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        cartService = CartService.getInstance();
        applicationLogic = ApplicationService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Long id = Long.parseLong(applicationLogic.getProductId(request.getRequestURI()));

        if(cartService.deleteCartItem(request,id)){
            String path = request.getContextPath() + "/cart?" +SUCCESSFUL_DELETE + "product"+ id.toString();
            response.sendRedirect(path);
        }
        else{
            request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
        }

    }
}
