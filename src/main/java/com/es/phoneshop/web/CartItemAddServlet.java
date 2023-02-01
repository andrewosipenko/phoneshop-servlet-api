package com.es.phoneshop.web;

import com.es.phoneshop.model.product.dao.ProductDao;
import com.es.phoneshop.model.product.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.exception.OutOfStockException;
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
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static com.es.phoneshop.model.product.service.ProductService.POSSIBLE_ERROR_MESSAGE;

public class CartItemAddServlet extends HttpServlet {
    private CartService cartService;
    private ProductService productService;
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = CartServiceImpl.getInstance();
        productService = ProductServiceImpl.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long productId = null;
        Map<Long, String> inputError = new HashMap<>();
        try {
            productId = productService.parseProductIdFromDeleteOrAddRequest(request);
            if (!request.getParameter("quantity").matches("^\\d+([\\.\\,]\\d+)?$")) {
                throw new ParseException("Not a number!", 0);
            }

            NumberFormat numberFormat = NumberFormat.getNumberInstance(request.getLocale());
            int quantity = numberFormat.parse(request.getParameter("quantity")).intValue();
            if (quantity == 0) {
                throw new ParseException("Not a number!", 0);
            }
            cartService.add(cartService.getCart(request), productId, quantity, request);
        } catch (ParseException | NumberFormatException exception) {
            inputError.put(productId, "Not a number!");
            request.getSession().setAttribute("inputError", inputError);
        } catch (OutOfStockException e) {
            inputError.put(productId, "Out of stock! Available:" + e.getAvailableStock());
            request.getSession().setAttribute("inputError", inputError);
        }
        if (inputError.isEmpty()) {
            request.getSession().setAttribute("inputError", inputError);
            response.sendRedirect(String.format("%s/products?message=%s was successfully added to the cart!",
                    request.getContextPath(),
                    productDao.getEntity(productId, POSSIBLE_ERROR_MESSAGE).getDescription()));
        } else {
            request.getSession().setAttribute("enteredQuantity", request.getParameter("quantity"));
            response.sendRedirect(String.format("%s/products", request.getContextPath()));
        }
    }
}
