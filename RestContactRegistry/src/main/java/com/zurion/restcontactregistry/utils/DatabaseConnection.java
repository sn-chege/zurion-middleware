package com.zurion.restcontactregistry.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author stanl
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/contact_registry";
    private static final String USER = "sean";
    private static final String PASSWORD = "21pilots";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

