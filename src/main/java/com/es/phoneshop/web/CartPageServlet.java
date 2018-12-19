package com.es.phoneshop.web;

import com.es.phoneshop.CartService.CartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import static com.es.phoneshop.projectConstants.Constants.SUCCESSFUL_ADD;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static com.es.phoneshop.projectConstants.Constants.*;

public class CartPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init();
        productDao = ArrayListProductDao.getInstance();
        cartService = CartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException{
        request.setAttribute(QUANTITY_ANSWER, request.getParameter(QUANTITY_ANSWER));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request,responce);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<String>  list = Arrays.asList(request.getParameterValues(QUANTITY));

        if(cartService.updateCart(request,list)){
            String path = request.getRequestURI() + "?" + QUANTITY_ANSWER + "=" + SUCCESSFUL_ADD;
            response.sendRedirect(path);
        }
        else{
            request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
        }

    }

}
