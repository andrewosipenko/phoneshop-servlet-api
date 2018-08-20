package com.es.phoneshop.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public class ProductListServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();

        productDao.save(new Product(1L,"A1B", "desc1",new BigDecimal("123.3"),
                Currency.getInstance(Locale.UK),0));

        productDao.save(new Product(2L,"A2B", "desc2",new BigDecimal("101.1"),
                Currency.getInstance(Locale.CANADA),123));

        productDao.save(new Product(3L,"A3B", "desc3",new BigDecimal("12323.3"),
                Currency.getInstance(Locale.KOREA),123123));

        productDao.save(new Product(4L,"A4B", "desc4",new BigDecimal("112323.3"),
                Currency.getInstance(Locale.JAPAN),1321223));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", productDao.getAllProducts());
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
