package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.cart.NotEnoughStockException;
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

public class CartPageServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] productsId = req.getParameterValues("productId");
        String[] quantities = req.getParameterValues("quantity");
        Cart cart = cartService.getCart(req.getSession());
        Map<Product, String> quantityErrors = new HashMap<>();
        for (int i = 0; i < productsId.length; i++) {
            Long productId = Long.valueOf(productsId[i]);
            Product product = productDao.getProduct(productId);
            Integer quantity = null;
            try {
                quantity = Integer.valueOf(quantities[i]);
            } catch (NumberFormatException ex) {
                quantityErrors.put(product, "Not a number");
            }
            if(quantity != null) {
                try {
                    cartService.updateCart(cart, product, quantity);
                } catch (NotEnoughStockException e) {
                    quantityErrors.put(product, "Not enough stock");
                }
            }
        }
        req.setAttribute("quantityErrors", quantityErrors);
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(req, resp);
    }
}
