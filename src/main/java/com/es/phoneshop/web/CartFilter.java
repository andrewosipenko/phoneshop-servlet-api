package com.es.phoneshop.web;

import com.es.phoneshop.CartService.Cart;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.es.phoneshop.projectConstants.Constants.CART;

public class CartFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session =((HttpServletRequest) servletRequest).getSession();
        if (session.getAttribute(CART) == null) {
            session.setAttribute(CART, new Cart());
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

}
