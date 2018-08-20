package com.es.phoneshop.web;

import model.ArrayListProductDao;
import model.Product;
import model.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class ProductListServlet extends HttpServlet {
    ProductDao productDao;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getInstance();
        Product product;
        for (int i = 0; i < 7; i++) {
            product = new Product();
            ProductIDGenerator.generateID(product);
            product.setCode(i + "");
            product.setDescription("description" + i);
            product.setPrice(new BigDecimal(i));
            product.setStock(i);
            productDao.save(product);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", productDao.findProducts());
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
