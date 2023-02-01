package com.es.phoneshop.web.servlet;

import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.HttpSessionCartService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCartItemServlet extends HttpServlet {
    private static final long serialVersionUID = -7535507829994819939L;

    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productIdString = request.getPathInfo().substring(1);
        Long productId = Long.valueOf(productIdString);

        cartService.delete(productId, request);

        response.sendRedirect(request.getContextPath() + "/cart?message=Product deleted successfully");
    }
}
