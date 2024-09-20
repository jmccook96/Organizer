package com.bookclub.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The {@code DatabaseManager} class manages the connection to the SQLite database.
 * It provides methods for accessing the database connection and closing the connection.
 * This class follows the singleton design pattern to ensure only one instance of the class is created.
 */
public class DatabaseManager {
    // Singleton implementation.
    private static DatabaseManager instance = null;
    private Connection connection;
    private static final String URL = "jdbc:sqlite:bookclub.db";

    /**
     * Constructor for the {@code DatabaseManager} class.
     * Initializes a connection to the SQLite database.
     */
    private DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    /**
     * Retrieves the instance of the DatabaseManager class.
     *
     * @return The instance of the DatabaseManager class.
     */
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Retrieves the connection to the SQLite database.
     *
     * @return The connection to the SQLite database.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes the connection to the SQLite database.
     * If a connection has been established, this method will attempt to close it.
     */
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
