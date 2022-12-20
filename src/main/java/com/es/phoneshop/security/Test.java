package com.es.phoneshop.security;

import java.sql.*;

public class Test {
    public static void main(String[] args) {
        Connection conn = null;

        try {

            String dbURL = "jdbc:sqlserver://WIN-B9PG27IQF4T;databaseName=MobilePhonesDb";
            String user = "user";
            String pass = "";
            conn = DriverManager.getConnection(dbURL, user, pass);
            Statement statement=conn.createStatement();
            ResultSet resultSet=statement.executeQuery("select * from Phones");
            while(resultSet.next()){
                System.out.println(resultSet.getString("Code"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("End of program");
            }
        }
    }
}
