package com.zurion.soapcontactregistry.utils;

/**
 *
 * @author stanl
 */
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String DRIVER;

    static {
        // Load database properties from db.properties file
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IOException("Unable to find db.properties");
            }
            
            Properties prop = new Properties();
            prop.load(input);

            // Assign values from db.properties
            URL = prop.getProperty("db.url");
            USER = prop.getProperty("db.username");
            PASSWORD = prop.getProperty("db.password");
            DRIVER = prop.getProperty("db.driver");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            // Load MySQL JDBC driver dynamically
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
