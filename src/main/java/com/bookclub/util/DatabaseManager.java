package com.bookclub.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static DatabaseManager instance = null;
    private Connection connection;
    private static final String URL = "jdbc:sqlite:book_club_app.db"; // Path to your SQLite DB

    private DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Failed to establish connection: " + e.getMessage());
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            System.err.println("Failed to re-establish connection: " + e.getMessage());
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close connection: " + e.getMessage());
            }
        }
    }
}
