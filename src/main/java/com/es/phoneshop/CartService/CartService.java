package com.es.phoneshop.CartService;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static com.es.phoneshop.ProjectConstants.Constants.CART;


public class CartService {
    private static CartService cartService;

    private CartService(){}

    public static CartService getInstance(){
        return new CartService();
    }

   public boolean emptyCartSession(HttpSession session){
       Cart cart = (Cart) session.getAttribute(CART);
       return cart == null;
   }

    public boolean addToCart(HttpSession session, Product product, int quantity) {
        if (this.emptyCartSession(session)) {
            Cart cart = new Cart();
            cart.save(new CartItem(product, quantity));
            session.setAttribute(CART, cart);

            return cart.containCartItem(new CartItem(product, quantity));
        }

        else{
            CartItem cartItem = new CartItem(product, quantity);
            Cart cart = (Cart) session.getAttribute(CART);
            if(cart.containCartItem(cartItem)){
                CartItem existingCartItem = new CartItem(product, quantity + cart.findByProduct(product).getQuantity());
                cart.deleteByProduct(product);
                cart.save(existingCartItem);
                session.setAttribute(CART, cart);
                return cart.containCartItem(existingCartItem);
            }
            cart.save(cartItem);
            session.setAttribute(CART, cart);
            return cart.containCartItem(cartItem);
        }

    }

    public boolean updateCart(HttpSession session, List<String> quantites){
       Cart cart = (Cart)session.getAttribute(CART);
       boolean flag = false;

       for(int i = 0; i < quantites.size(); ++i){
           if(Integer.parseInt(quantites.get(i)) > 0 && Integer.parseInt(quantites.get(i)) < cart.getCartList().get(i).getProduct().getStock()){
               cart.getCartList().get(i).setQuantity(Integer.parseInt(quantites.get(i)));
               flag = true;
           }

       }
        session.setAttribute(CART,cart);
       return flag;
    }
}
