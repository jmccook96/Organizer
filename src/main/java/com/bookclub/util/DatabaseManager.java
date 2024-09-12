package com.bookclub.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    // Singleton implementation.
    private static DatabaseManager instance = null;
    private Connection connection;
    private static final String URL = "jdbc:sqlite:bookclub.db";
    
    private DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }
    
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager(); // I personally hate this approach.
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqlEx) {
                System.err.println(sqlEx);
            }
        }
    }
}
