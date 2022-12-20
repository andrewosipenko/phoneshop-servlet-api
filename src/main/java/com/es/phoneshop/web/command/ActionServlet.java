package com.es.phoneshop.web.command;

import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.web.actions.AddToCartAction;
import com.es.phoneshop.web.actions.CartPageAction;
import com.es.phoneshop.web.actions.DeleteCartItemAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ActionServlet extends HttpServlet {



    private Map<String,Action> actionMap = new HashMap<String,Action>();
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        actionMap.put("/cart/addToCart", new AddToCartAction(config));
        actionMap.put("/cart", new CartPageAction(config));
        actionMap.put("/cart/deleteCartItem", new DeleteCartItemAction(config));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionKey = request.getServletPath();
        Action action = actionMap.get(actionKey);
        if(actionKey.equals("/cart/addToCart")){
            try {

                response.sendRedirect(action.execute(request, response));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(actionKey.contains("/cart/deleteCartItem")){

            action = actionMap.get(actionKey.substring(0,actionKey.length()));
            try {
                response.sendRedirect(action.execute(request, response));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
