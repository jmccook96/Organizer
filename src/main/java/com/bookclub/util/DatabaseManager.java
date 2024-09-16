package com.bookclub.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static DatabaseManager instance = null;
    private Connection connection;
    private static final String URL = "jdbc:sqlite:book_club_app.db";

    private DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new DatabaseInitializationException("Failed to establish connection", e);
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to re-establish connection", e);
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DatabaseConnectionException("Failed to close connection", e);
            }
        }
    }
}

//Custom Exception, classes maybe they give more info?
class DatabaseInitializationException extends RuntimeException {
    public DatabaseInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}

class DatabaseConnectionException extends RuntimeException {
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}