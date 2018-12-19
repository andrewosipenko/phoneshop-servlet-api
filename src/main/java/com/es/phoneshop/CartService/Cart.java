package com.es.phoneshop.CartService;

import com.es.phoneshop.model.product.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class Cart {
    private List<CartItem> cartList;

    private BigDecimal totalPrice;


    public Cart() {
        cartList = new ArrayList<>();
    }

    public Cart(Cart cart){
        this.cartList = new ArrayList<>(cart.cartList);
        this.totalPrice = cart.totalPrice;
    }

    public boolean isEmpty(){
        return cartList.isEmpty();
    }
    public void save(CartItem cartItem) {
        if(cartItem == null)
            throw new IllegalArgumentException("Invalid product to add");
        cartList.add(cartItem);
    }

    public boolean containCartItem(CartItem cartItem){
        return this.cartList.stream().filter(x->x.getProduct().equals(cartItem.getProduct())).findFirst().isPresent();
    }

    public boolean containProduct(Product product){
        return cartList.stream().filter(x->x.getProduct().equals(product)).findFirst().isPresent();
    }

    public CartItem findByProduct(Product product){
        Optional<CartItem> cart = cartList.stream().filter(x->x.getProduct().equals(product)).findFirst();
        return cart.get();

    }
    public boolean find(CartItem cartItem){
        return this.cartList.stream().filter(x->x.equals(cartItem)).findFirst().isPresent();
    }
    public void delete(CartItem cartItem) {
        if(!this.find(cartItem))
            throw new IllegalArgumentException("Cant find such cartItem");
        cartList.remove(cartItem);
    }

    public void deleteByProduct(Product product){
        cartList.remove(findByProduct(product));
    }

    public List<CartItem> getCartList() {
        return this.cartList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        if(cartItemList != null)
        this.cartList = cartItemList;
    }

    public void countTotalPrice(){
        BigDecimal totalAmmount = BigDecimal.ZERO;

        for(CartItem cartItem : cartList){
           totalAmmount = totalAmmount.add(cartItem.getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
        }

        totalPrice = totalAmmount;
    }

    public BigDecimal getTotalPrice(){
        return totalPrice;
    }


}
