package com.es.phoneshop.order;

import java.util.UUID;
import com.es.phoneshop.CartService.Cart;

public class Order {
    private String name;
    private String lastName;
    private String phoneNumber;
    private String deloveryAdress;

    private Cart cart;

    private String uniqueAdress;

    public Cart getCart() {
        return cart;
    }

    public Order(String name, String lastName, String phoneNumber, String deloveryAdress, Cart cart){
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.deloveryAdress = deloveryAdress;
        this.cart = new Cart(cart);

        this.setUnqieAdress();
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setUniqueAdress(String uniqueAdress) {
        this.uniqueAdress = uniqueAdress;
    }

    private void setUnqieAdress(){
        uniqueAdress = UUID.randomUUID().toString();
    }
    public String getUniqueAdress(){
        return uniqueAdress;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDeloveryAdress() {
        return deloveryAdress;
    }

    public void setDeloveryAdress(String deloveryAdress) {
        this.deloveryAdress = deloveryAdress;
    }

    public void setName(String name) {
        this.name = name;
    }
}
