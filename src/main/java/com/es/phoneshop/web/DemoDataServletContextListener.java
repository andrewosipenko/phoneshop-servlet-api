package com.es.phoneshop.web;

import com.es.phoneshop.model.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.PriceRecord;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

public class DemoDataServletContextListener implements ServletContextListener {
    private static final String CURRENCY = "USD";

    private ProductDao productDao;

    public DemoDataServletContextListener() throws ProductNotFoundException {
        this.productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        boolean insertDemoData = Boolean.valueOf(servletContextEvent.getServletContext().getInitParameter("insertDemoData"));
        if (insertDemoData) {
            for (Product product : getSampleProducts()) {
                try {
                    List<PriceRecord> records = new ArrayList<>();
                    records.add(new PriceRecord(product.getPrice(), product.getCurrency()
                            , new Date(1212121212121L)));
                    product.setPriceHistory(records);
                    productDao.save(product);
                } catch (ProductNotFoundException e) {
                    throw new RuntimeException("Failed to insert demo data", e);
                }
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    private List<Product> getSampleProducts() {
        List<Product> result = new ArrayList<>();
        Currency usd = Currency.getInstance(CURRENCY);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/phonesdb", "root", "1234");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Phones");
            while (resultSet.next()) {
                String code = resultSet.getString("Code");
                String description = resultSet.getString("Description");
                String price = resultSet.getString("Price");
                String currency = resultSet.getString("Currency");
                String stock = resultSet.getString("Stock");
                String imageUrl = resultSet.getString("ImageUrl");
                result.add(
                        new Product(
                                code,
                                description,
                                new BigDecimal(price),
                                Currency.getInstance(currency.toUpperCase()),
                                Integer.valueOf(stock),
                                imageUrl
                        )
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
