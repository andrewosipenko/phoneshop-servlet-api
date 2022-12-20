package com.es.phoneshop.web.actions;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.DefaultCartService;
import com.es.phoneshop.web.command.Action;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartItemAction implements Action {
    private CartService cartService;

    public DeleteCartItemAction(ServletConfig config){
        //super.init(config);
        cartService = DefaultCartService.getInstance();
    }


    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productId = request.getPathInfo().substring(1);

        Cart cart =cartService.getCart(request.getSession());
        cartService.delete(cart,Long.valueOf(productId));

        return request.getContextPath() + "/cart?message=Cart item removed successfully";
    }
}
