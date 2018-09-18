package com.es.phoneshop.web;

import com.es.phoneshop.model.ArrayListProductDao;
import com.es.phoneshop.model.CartService;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ProductDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

public class CartPageServlet extends HttpServlet {
    private CartService cartService;
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = cartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        String[] errors = new String[productIds.length];
        if(request.getParameter("delete") != null){
            System.out.println("delete id true");
            deleteItem(request);
            doGet(request, response);
            return;
        }
        boolean hasErrors = false;
        for (int i = 0; i < productIds.length; i++) {
            Product product = productDao.getProduct(Long.valueOf(productIds[i]));
            Locale locale = request.getLocale();
            try {
                int quantity = DecimalFormat.getInstance(locale).parse(quantities[i]).intValue();
                if (quantity < 0) {
                    throw new IllegalArgumentException();
                }
                cartService.update(cartService.getCart(request), product, quantity);
            } catch (NumberFormatException ex) {
                errors[i] = "error";
                hasErrors = true;
            } catch (ParseException ex) {
                errors[i] = "error";
                hasErrors = true;
            } catch (IllegalArgumentException ex) {
                errors[i] = "toMuch";
                hasErrors = true;
            }
        }
        if (hasErrors) {
            request.setAttribute("errors", errors);
            request.setAttribute("quantities", quantities);
            doGet(request, response);
        } else {
             response.sendRedirect("cart");
        }
    }

    private void deleteItem(HttpServletRequest request){
        Long deleteId = Long.parseLong(request.getParameter("delete"));
        Product product = productDao.getProduct(deleteId);
        cartService.delete(cartService.getCart(request), product);

    }

}
