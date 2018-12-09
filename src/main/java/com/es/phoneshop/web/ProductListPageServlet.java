package com.es.phoneshop.web;

import com.es.phoneshop.logic.ProductLogic;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;


public class ProductListPageServlet extends HttpServlet {
    private ArrayListProductDao products;
    private ProductLogic productLogic = ProductLogic.getInstance();

   @Override
   public void init(ServletConfig config) throws ServletException{
     super.init();
     this.products = ArrayListProductDao.getInstance();
   }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", productLogic.findProducts(request));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);

    }

    private List<Product> getSampleProducts(){
        List<Product> result = products.findProducts();
        return result;
    }
}
