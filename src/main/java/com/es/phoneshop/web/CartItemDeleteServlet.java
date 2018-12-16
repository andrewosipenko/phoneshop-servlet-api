package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.impl.CartServiceImpl;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

public class CartItemDeleteServlet extends HttpServlet{
    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getInstance();
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = loadProduct(request);
        Cart cart = cartService.getCart(request.getSession());
        cartService.delete(cart, product);
        response.sendRedirect(request.getContextPath()
        + "/cart?message=Cart item " + product.getCode() + " removed successfully");
    }

    private Product loadProduct(HttpServletRequest request) throws NoSuchElementException{
        String idString = request.getRequestURI().substring((request.getContextPath() + request.getServletPath()).length()+1);
        Long id = Long.parseLong(idString);
        return productDao.getProduct(id);
    }
}
