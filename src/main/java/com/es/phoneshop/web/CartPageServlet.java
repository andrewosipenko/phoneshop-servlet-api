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
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class CartPageServlet extends HttpServlet{

    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getInstance();
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request.getSession()));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        Cart cart = cartService.getCart(request.getSession());
        Map<Long, String> quantityErrors = new HashMap<>();

        for(int i = 0; i < productIds.length; i++){
            Long productId = Long.valueOf(productIds[i]);
            Product product = productDao.getProduct(productId);
            Integer quantity = null;
            try {
                quantity = Integer.valueOf(quantities[i]);
            }catch (NumberFormatException e) {
                quantityErrors.put(product.getId(), "Not a number");
            }
            if (quantity != null){
                try{
                    cartService.updateCart(cart, product, quantity);
                }catch (NoSuchElementException e){
                    quantityErrors.put(product.getId(), "Not enough stock");
                }
            }
        }
        request.setAttribute("quantityErrors", quantityErrors);

        if(quantityErrors.isEmpty()){
            response.sendRedirect(request.getRequestURI() + "?message=cart update successfully");
        }else {
            request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
        }
    }
}
