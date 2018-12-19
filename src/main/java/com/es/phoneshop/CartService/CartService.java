package com.es.phoneshop.CartService;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.es.phoneshop.projectConstants.Constants.*;


public class CartService {
    private static CartService cartService;
    private ProductDao productDao = ArrayListProductDao.getInstance();

    private CartService(){}

    public static CartService getInstance() {
        if (cartService == null) {
            synchronized (CartService.class) {
                if (cartService == null) {
                    cartService = new CartService();
                }
            }

        }
        return cartService;
    }

   public boolean emptyCartSession(HttpSession session){
       Cart cart = (Cart) session.getAttribute(CART);
       return cart == null;
   }

    public boolean addToCart(HttpServletRequest request, Product product, String ammount) {
        HttpSession session = request.getSession();
        if(!ammount.matches("[0-9]+")){
           request.setAttribute(QUANTITY_ANSWER, NOT_A_NUMBER);
           return false;
       }
        int quantity = Integer.parseInt(ammount);
        if(quantity < 0){
            request.setAttribute(QUANTITY_ANSWER, UNSUCCESSFUL_UPDATE);
            return false;
        }
        if(quantity > product.getStock()){
            request.setAttribute(QUANTITY_ANSWER, OUT_OF_STOCK);
            return false;
        }
        if (this.emptyCartSession(session)) {
            Cart cart = new Cart();
            cart.save(new CartItem(product, quantity));
            session.setAttribute(CART, cart);
            return true;
        }
        else{
            CartItem cartItem = new CartItem(product, quantity);
            Cart cart = (Cart) session.getAttribute(CART);
            if(cart.containCartItem(cartItem)){
                CartItem existingCartItem = new CartItem(product, quantity + cart.findByProduct(product).getQuantity());
                cart.deleteByProduct(product);
                cart.save(existingCartItem);
                cart.countTotalPrice();
                session.setAttribute(CART, cart);
                return cart.containCartItem(existingCartItem);
            }
            cart.save(cartItem);
            cart.countTotalPrice();
            session.setAttribute(CART, cart);
            return cart.containCartItem(cartItem);
        }



    }

    public boolean updateCart(HttpServletRequest request, List<String> quantites){
        Cart cart = (Cart) request.getSession().getAttribute(CART);
        if(cart.isEmpty()){
            request.setAttribute(QUANTITY_ANSWER, "Can't update empty cart.");
            return false;
        }
        for(String quantity : quantites)
            if(!quantity.matches("[0-9]+")){
                request.setAttribute(QUANTITY_ANSWER, NOT_A_NUMBER);
                return false;
            }

       for(int i = 0; i < quantites.size(); ++i){
           if(Integer.parseInt(quantites.get(i)) > 0 && Integer.parseInt(quantites.get(i)) < cart.getCartList().get(i).getProduct().getStock()){
               cart.getCartList().get(i).setQuantity(Integer.parseInt(quantites.get(i)));
           }
           else {
               request.setAttribute(QUANTITY_ANSWER, OUT_OF_STOCK);
               return false;
           }
       }
        cart.countTotalPrice();
       request.getSession().setAttribute(CART,cart);
       return true;
    }

    public boolean deleteCartItem(HttpServletRequest request, Long productId){
        Cart cart = (Cart) request.getSession().getAttribute(CART);
        Product product = productDao.getProduct(productId);
        if(cart.containProduct(product)){
            cart.deleteByProduct(product);
            cart.countTotalPrice();
            return true;
        }
        else{
            request.setAttribute(DELETE_ANSWER,NO_ELEMENT_IN_CART );
            return false;
        }

    }
}
