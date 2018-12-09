package com.es.phoneshop.web;

import com.es.phoneshop.CartService.Cart;
import com.es.phoneshop.CartService.CartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;

import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

import static com.es.phoneshop.ProjectConstants.Constants.*;

public class CartPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse responce) throws ServletException, IOException{
        HttpSession session = request.getSession();
        request.setAttribute(CART, session.getAttribute(CART));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request,responce);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        List<String>  list = Arrays.asList(request.getParameterValues(QUANTITY));

        Cart cart = (Cart) session.getAttribute(CART);
        if(cartService.updateCart(session,list)){
            String path = request.getRequestURI() + "?" + QUANTITY_ANSWER + "=" + "products_were_succesfully_added)";
            request.getRequestDispatcher(path).forward(request, response);
        }
        else{
           request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request,response);
        }

    }

}
