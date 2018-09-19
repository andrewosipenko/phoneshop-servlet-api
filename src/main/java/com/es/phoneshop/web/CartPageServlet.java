package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.NotEnoughException;
import com.es.phoneshop.exceptions.UnderZeroException;
import com.es.phoneshop.model.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

public class CartPageServlet extends HttpServlet {
    private CartService cartService;
    private ProductDAO productDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = CartServiceImpl.getInstance();
        productDAO = ArrayListProductDAO.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean ifAnError = false;
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        String[] errors = new String[productIds.length];
        String deleteValue = request.getParameter("delete");
        Locale locale = request.getLocale();
        ResourceBundle res = ResourceBundle.getBundle("messages", locale);
        Cart cart = cartService.getCart(request);
        int quantity;
        if (deleteValue != null) {
            int deletedProductId = Integer.valueOf(deleteValue);
            Product product = cartService.getCart(request).getCartItems().get(deletedProductId).getProduct();
            cartService.delete(cart, product, deletedProductId);
            request.setAttribute("successDelete", true);
            response.sendRedirect(request.getRequestURI() + "?successDelete");
            return;
        } else {
            for (int i = 0; i < productIds.length; i++) {
                Product product = productDAO.getProduct(Long.valueOf(productIds[i]));
                try {
                    quantity = DecimalFormat.getInstance(locale).parse(quantities[i]).intValue();
                    if (quantity < 0) {
                        throw new UnderZeroException(UnderZeroException.UNDER_ZERO_MESSAGE);
                    }
                    cartService.update(cartService.getCart(request), product, quantity);
                    /*request.setAttribute("successUpdate", quantity);
                    response.sendRedirect(request.getRequestURI() + "?successUpdate=" + quantity);
                    return;*/
                } catch (ParseException e) {
                    errors[i] = res.getString("error.number.format");
                    ifAnError = true;
                } catch (UnderZeroException e) {
                    errors[i] = res.getString("error.number.must.being");
                    ifAnError = true;
                } catch (NotEnoughException e){
                    errors[i] = res.getString("error.number.amount");
                    ifAnError = true;
                }
            }
        }
        if (ifAnError) {
            request.setAttribute("quantities", quantities);
            request.setAttribute("error", true);
            request.setAttribute("errors", errors);
            doGet(request, response);
        }
        else {
            request.setAttribute("successUpdate", true);
            response.sendRedirect(request.getRequestURI() + "?successUpdate");

        }
    }
}
