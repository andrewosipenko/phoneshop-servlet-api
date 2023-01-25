package com.es.phoneshop.web;

import com.es.phoneshop.model.product.service.CartService;
import com.es.phoneshop.model.product.service.ProductService;
import com.es.phoneshop.model.product.service.impl.CartServiceImpl;
import com.es.phoneshop.model.product.service.impl.ProductServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {
    private CartService cartService;
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = CartServiceImpl.getInstance();
        productService = ProductServiceImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        cartService.delete(
                cartService.getCart(request),
                productService.parseProductIdFromDeleteOrAddRequest(request),
                request);
        response.sendRedirect(String.format("%s/cart?message=Item was successfully deleted from the cart!", request.getContextPath()));
    }
}
